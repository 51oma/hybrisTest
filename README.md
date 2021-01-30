# Test Hybris Task

### Install instructions:
* Download SAP Hybris 1808 or newer version
* Download source code of test task from github:
git clone -b ACC-01 git@github.com:51oma/hybrisTest.git
* Comment templates in localextensions.xml and add new extentions like this:
```xml
    <!--<extension name='yacceleratorbackoffice' />
    <extension name='yacceleratorinitialdata' />
    <extension name='yacceleratorfulfilmentprocess' />
    <extension name='yacceleratorstorefront' />
    <extension name='ycommercewebservices' />
    <extension name='ycommercewebservicestest' />-->
	
    <extension name='sartbackoffice' />
    <extension name='sartcore' />
    <extension name='sartfacades' />
    <extension name='sartfulfilmentprocess' />
    <extension name='sartinitialdata' />
    <extension name='sartstorefront' />
    <extension name='sarttest' />
    <extension name='sartws' />	
```
* Execute ant clean all initialize
* Start Hybris system: hybrisserver.bat debug
* Go to the start page for electronics shop: https://localhost:9002/store/?site=electronics
* Put csv file with transactions to the folder "hybris\data\acceleratorservices\import\master\electronics"
* Go to the transaction page: https://localhost:9002/store/electronics/en/transactions to see your importing transactions
