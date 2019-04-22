import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

//-----------------------------verify drag and drop on html5----------------------------------------
WebUI.openBrowser('https://www.w3schools.com/html/html5_draganddrop.asp')

CustomKeywords.'com.kms.katalon.keyword.draganddrop.DragAndDropKeywords.dragAndDropHtml5'(findTestObject('Page_HTML5 Drag and Drop/img_drag1'), 
    findTestObject('Page_HTML5 Drag and Drop/div_div2'))

WebUI.verifyElementAttributeValue(findTestObject('Page_HTML5 Drag and Drop/div_div2'), 'innerHTML', '<img src="img_w3slogo.gif" draggable="true" ondragstart="drag(event)" id="drag1" alt="W3Schools">', 
    0)

//-----------------------------verify drag and drop on JQuery----------------------------------------
WebUI.navigateToUrl('http://jqueryui.com/droppable/#default')

CustomKeywords.'com.kms.katalon.keyword.draganddrop.DragAndDropKeywords.dragAndDropJquery'(findTestObject('Page_Droppable  jQuery UI/div_draggable'), 
    findTestObject('Page_Droppable  jQuery UI/div_droppable'))

//-----------------------------verify drag and drop using dragAndDrop------------------------------------
WebUI.navigateToUrl('http://demo.guru99.com/test/drag_drop.html')

CustomKeywords.'com.kms.katalon.keyword.draganddrop.DragAndDropKeywords.dragAndDrop'(findTestObject('Object Repository/Page_Drag and Drop Action/old_BANK'), 
    findTestObject('Object Repository/Page_Drag and Drop Action/new_Bank'))
//WebUI.closeBrowser()

//-----------------------------verify drag and drop using dragAndDropBy------------------------------------
WebUI.navigateToUrl('http://demo.guru99.com/test/drag_drop.html')

CustomKeywords.'com.kms.katalon.keyword.draganddrop.DragAndDropKeywords.dragAndDropBy'(findTestObject('Object Repository/Page_Drag and Drop Action/old_BANK'),findTestObject('Object Repository/Page_Drag and Drop Action/new_Bank'),0,0)

