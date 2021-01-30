package com.sart.core.dataimport.task;

import com.sart.core.model.AccBatchModel;
import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.acceleratorservices.dataimport.batch.task.AbstractImpexRunnerTask;
import de.hybris.platform.servicelayer.impex.ImpExResource;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.Session;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @author Created by Maxim.Olkhin@masterdata.ru
 */
public abstract class AbstractAccImpexRunnerTask extends AbstractImpexRunnerTask {

    private static final Logger LOG = Logger.getLogger(AbstractImpexRunnerTask.class);
    private ModelService modelService;

    protected AbstractAccImpexRunnerTask(ModelService modelService) {
        this.modelService = modelService;
    }

    /**
     * Store batch information before and after file importing.
     *
     * @param header batch header
     * @return batch header
     */
    @Override
    public BatchHeader execute(final BatchHeader header) {
        Assert.notNull(header);
        Assert.notNull(header.getEncoding());
        if (CollectionUtils.isNotEmpty(header.getTransformedFiles())) {
            final Session localSession = getSessionService().createNewSession();
            try {
                AccBatchModel batchModel = createBatch(header.getFile());
                int countUnresolvedLines = 0;
                for (final File file : header.getTransformedFiles()) {
                    countUnresolvedLines += processBatchFile(file, header.getEncoding());
                }
                updateBatch(batchModel, countUnresolvedLines);
            } finally {
                getSessionService().closeSession(localSession);
            }
        }
        return header;
    }

    private void updateBatch(AccBatchModel batchModel, int countUnresolvedLines) {
        batchModel.setEndDate(new Date());
        batchModel.setNumberOfNewTrans(batchModel.getTotalNumberOfRows() - countUnresolvedLines);
        modelService.save(batchModel);
    }

    private AccBatchModel createBatch(File file) {
        AccBatchModel batchModel = modelService.create(AccBatchModel.class);
        batchModel.setFilename(file.getName());
        batchModel.setStartDate(new Date());
        try {
            batchModel.setTotalNumberOfRows((int) Files.lines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8).count());
        } catch (IOException e) {
            LOG.error(MessageFormat.format("Can not count lines for file {0}", file.getName()), e);
        }
        modelService.save(batchModel);
        return batchModel;
    }

    /**
     * Process an impex file using the given encoding
     *
     * @param file
     * @param encoding
     * @return count of unresolved lines
     */
    private int processBatchFile(final File file, final String encoding) {
        try (final FileInputStream fis = new FileInputStream(file)) {
            final ImportConfig config = getImportConfig();
            if (config == null) {
                LOG.error(String.format("Import config not found. The file %s won't be imported.",
                        file == null ? null : file.getName()));
                return 0;
            }
            final ImpExResource resource = new StreamBasedImpExResource(fis, encoding);
            config.setScript(resource);
            final ImportResult importResult = getImportService().importData(config);
            if (importResult.isError() && importResult.hasUnresolvedLines()) {
                LOG.error(importResult.getUnresolvedLines().getPreview());
                return importResult.getUnresolvedLines().getLinesToSkip();
            }
        } catch (final IOException | IllegalStateException e) {
            LOG.error("Error occured while process file: " + file, e);
        }
        return 0;
    }

}
