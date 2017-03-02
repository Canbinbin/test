package com.mcpfp.web.common.util.recognition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.abbyy.FREngine.BlockTypeEnum;
import com.abbyy.FREngine.CodePageEnum;
import com.abbyy.FREngine.DitheringMethodEnum;
import com.abbyy.FREngine.Engine;
import com.abbyy.FREngine.IBlock;
import com.abbyy.FREngine.IDocumentProcessingParams;
import com.abbyy.FREngine.IEngine;
import com.abbyy.FREngine.IFRDocument;
import com.abbyy.FREngine.IFRPage;
import com.abbyy.FREngine.IFRPages;
import com.abbyy.FREngine.IIntsCollection;
import com.abbyy.FREngine.ILanguageDatabase;
import com.abbyy.FREngine.ILayout;
import com.abbyy.FREngine.IObjectsExtractionParams;
import com.abbyy.FREngine.IPageAnalysisParams;
import com.abbyy.FREngine.IPrepareImageMode;
import com.abbyy.FREngine.IRegion;
import com.abbyy.FREngine.ITextBlock;
import com.abbyy.FREngine.ITextExportParams;
import com.abbyy.FREngine.ITextLanguage;
import com.abbyy.FREngine.NoiseModelEnum;
import com.abbyy.FREngine.TXTExportFormatEnum;
import com.abbyy.FREngine.TextEncodingTypeEnum;

public class OCREngine {
	private static OCREngine singleton;
	private IEngine engine = null;//OCR引擎
		
	private OCREngine(){
		this.engineInitial();
	}	
	
	/**
	 * 引擎初始化
	 * 由于采用OCREngine类的静态方法进行引擎初始化，只有当OCREngine类被实际调用时，才会开始加载引擎，
	 * 这将延长首次调用后的操作时间
	 * 同时，该方法也提供了一个在应用加载阶段就初始化引擎的手段
	 * @return OCREngine
	 */
	public static OCREngine initial(){
		if(singleton == null){
			synchronized(OCREngine.class){
				if(singleton == null){
					singleton = new OCREngine();
					return singleton;
				}
			}
		}
		return singleton;
	}
	
	/**
	 * 获取OCR引擎类
	 * @return OCREngine 单例对象
	 */
	public static OCREngine getSingleton(){
		if(singleton == null){
			throw new IllegalStateException("引擎尚未初始化，请先调用Initial方法");
		}
		return singleton;
	}
	
	/**
	 * 引擎初始化
	 */
	public void engineInitial(){
		if(engine == null){
			engine = Engine.Load(OCRConfig.getInstance().getDllPath(), OCRConfig.getInstance().getActiveCode());
			engine.LoadPredefinedProfile( "TextExtraction_Accuracy" );
		}
	}
	
	/**
	 * 图片识别-默认
	 * @return String
	 */
	public synchronized String processImage(String imagePath) {
		if(null == engine){
			throw new IllegalStateException("引擎尚未初始化");
		}
		String result = "";
		
		// 新建文档
		IFRDocument document = engine.CreateFRDocument();
		
		//IPrepareImageMode prepareImageMode = engine.CreatePrepareImageMode();
        //pim.XResolutionToOverwrite = 300;
        //pim.YResolutionToOverwrite = 300;
        //pim.CreatePreview = true; //创建图像预览—— document.Pages[0].ImageDocument.PreviewImage 预览图像
		//prepareImageMode.setAutoOverwriteResolution(true);
		//prepareImageMode.setCorrectSkew(true);
		
		try {
			// 设置图片
			document.AddImageFile( imagePath, null, null );
			
			// 设置语言
			IDocumentProcessingParams documentProcessingParams = engine.CreateDocumentProcessingParams();
			documentProcessingParams.getPageProcessingParams().getRecognizerParams().SetPredefinedTextLanguage("ChinesePRC,English");
			documentProcessingParams.getPageProcessingParams().getRecognizerParams().setLowResolutionMode(true);//低分辨率模式
			documentProcessingParams.getPageProcessingParams().getObjectsExtractionParams().setEnableAggressiveTextExtraction(true);//尽可能识别出图片中的文字
			
			int pageCount = document.getPages().getCount();
            for (int i = 0; i < pageCount; i++){
                int newResolution = document.getPages().getElement(i).DetectResolution(documentProcessingParams.getPageProcessingParams().getObjectsExtractionParams(), 300, 600);
                if (newResolution > 0){
                	document.getPages().getElement(i).getImageDocument().ChangeResolution(newResolution);
                }
            }
            
			// 处理文档
			document.Process(documentProcessingParams);
			
			result = document.getPlainText().getText();
		}catch(Exception ex) {
			throw ex;
		}finally {
			document.Close(); // 关闭文档
		}
		
		return result;
	}
	
	/**
	 * 图片识别-代表个图片（把整个图片看作一个文本域）
	 * @return String
	 */
	public synchronized String  processImageWithGrid(String imagePath) {
		if(null == engine){
			throw new IllegalStateException("引擎尚未初始化");	
		}
		String result = "";		
		
		IPrepareImageMode prepareImageMode = engine.CreatePrepareImageMode();
		prepareImageMode.setAutoOverwriteResolution(true);
		
		// Create document
		IFRDocument document = engine.CreateFRDocumentFromImage(imagePath, prepareImageMode);
		
		try {
			IObjectsExtractionParams objectParam = engine.CreateObjectsExtractionParams();
			objectParam.setEnableAggressiveTextExtraction(true);
			
			//*****************区域识别关键代码********************
			IFRPages pages = document.getPages();
			for (int i = 0; i < pages.getCount(); i++){
				IFRPage page = pages.getElement(i);
				ILayout layout = engine.CreateLayout();
				int count = createRecognitionBlock(layout, getCoordinate(page));
				IIntsCollection ilc = engine.CreateIntsCollection();
				
                while (count > 0){
                    count--;
                    ilc.Add(count);
                }
                if (page.getLayout() == null){
                    page.setLayout(layout);
                }else{
                    page.getLayout().CopyFrom(layout);
                }
                page.RecognizeBlocks(ilc, null, null);
			}
			//*****************区域识别关键代码********************
			
            ITextExportParams param = document.getApplication().CreateTextExportParams();
            param.setExportFormat(TXTExportFormatEnum.TEF_TXT);
            param.setEncodingType(TextEncodingTypeEnum.TET_UTF8);
            param.setCodePage(CodePageEnum.CP_Chinese_Simpl_GB);
            			
/*			// Create new recognition language
			IDocumentProcessingParams documentProcessingParams = engine.CreateDocumentProcessingParams();
			documentProcessingParams.getPageProcessingParams().getRecognizerParams().SetPredefinedTextLanguage("ChinesePRC,English");
            
			// Process document
			document.Process(documentProcessingParams);*/
			
			result = document.getPlainText().getText();
		}catch(Exception ex) {
			System.out.println(ex.getStackTrace());
		}finally {
			document.Close(); // Close document
		}
		
		return result;
	}
		
	/**
	 * 图片识别-需要特殊处理的图片
	 * @return String
	 */
	public synchronized String  processImageWithSpecial(String imagePath) {
		if(null == engine){
			throw new IllegalStateException("引擎尚未初始化");	
		}
		String result = "";		
		
		//下面被注释的代码会导致红色背景的图片无法被识别
		//IPrepareImageMode prepareImageMode = engine.CreatePrepareImageMode();
		//prepareImageMode.setAutoOverwriteResolution(true);
		
		// Create document
		IFRDocument document = engine.CreateFRDocumentFromImage(imagePath, null);
		
		try {
			IObjectsExtractionParams objectParam = engine.CreateObjectsExtractionParams();
			objectParam.setEnableAggressiveTextExtraction(true);
			
			//*****************区域识别关键代码********************
			IFRPages pages = document.getPages();
			for (int i = 0; i < pages.getCount(); i++){
				IFRPage page = pages.getElement(i);
				ILayout layout = engine.CreateLayout();
				int count = createRecognitionBlock(layout, getCoordinate(page));
				IIntsCollection ilc = engine.CreateIntsCollection();
				
                while (count > 0){
                    count--;
                    ilc.Add(count);
                }

                if (page.getLayout() == null){
                    page.setLayout(layout);
                }else{
                    page.getLayout().CopyFrom(layout);
                }
                page.RecognizeBlocks(ilc, null, null);
			}
			//*****************区域识别关键代码********************
			
            ITextExportParams param = document.getApplication().CreateTextExportParams();
            param.setExportFormat(TXTExportFormatEnum.TEF_TXT);
            param.setEncodingType(TextEncodingTypeEnum.TET_UTF8);
            param.setCodePage(CodePageEnum.CP_Chinese_Simpl_GB);
            			
			// Create new recognition language
			IDocumentProcessingParams documentProcessingParams = engine.CreateDocumentProcessingParams();            
			int pageCount = document.getPages().getCount();
            for (int i = 0; i < pageCount; i++){
                int newResolution = document.getPages().getElement(i).DetectResolution(documentProcessingParams.getPageProcessingParams().getObjectsExtractionParams(), 150, 600);
                if (newResolution > 0){
                	document.getPages().getElement(i).getImageDocument().ChangeResolution(newResolution);
                }
            }
            
			// Process document
			document.Process(documentProcessingParams);
			
			result = document.getPlainText().getText();
		}catch(Exception ex) {
			System.out.println(ex.getStackTrace());
		}finally {
			document.Close(); // Close document
		}
		
		return result;
	}

	/**
	 * 图片识别-测试用
	 * @return String
	 */
	public synchronized String  processImageWithTest(String imagePath) {
		if(null == engine){
			throw new IllegalStateException("引擎尚未初始化");	
		}
		String result = "";		
		
		IPrepareImageMode prepareImageMode = engine.CreatePrepareImageMode();
        //pim.XResolutionToOverwrite = 300;
        //pim.YResolutionToOverwrite = 300;
        //pim.CreatePreview = true; //创建图像预览—— document.Pages[0].ImageDocument.PreviewImage 预览图像
		prepareImageMode.setAutoOverwriteResolution(true);
		prepareImageMode.setCorrectSkew(true);
		prepareImageMode.setDiscardColorImage(true);
		
		// Create document
		IFRDocument document = engine.CreateFRDocumentFromImage(imagePath, prepareImageMode);
		
		try {
            int pageCount = document.getPages().getCount();
			for (int i = 0; i < pageCount; i++){
				//document.getPages().getElement(i).getImageDocument().ChangeResolution(300);//改变分辨率
				document.getPages().getElement(i).getImageDocument().SmoothTexture();//使图片和文字平滑
				document.getPages().getElement(i).getImageDocument().RemoveNoise(NoiseModelEnum.NM_WhiteNoise, false);
				document.getPages().getElement(i).getImageDocument().CropImage();//Crop Image
				document.getPages().getElement(i).getImageDocument().EnhanceLocalContrast();//Enhance local contrast
				document.getPages().getElement(i).getImageDocument().SmoothTexture();//smooth the texture
				document.getPages().getElement(i).getImageDocument().DitherImage(DitheringMethodEnum.DM_Bayer);//dither image
				document.getPages().getElement(i).getImageDocument().EqualizeBrightness(true);//Equalize brightness and make the background white
				//document.getPages().getElement(i).getImageDocument().CorrectSkew(CorrectSkewModeEnum.CSM_CorrectSkewByHorizontalText.getValue());
			}
			
			// Create new recognition language
			IDocumentProcessingParams documentProcessingParams = engine.CreateDocumentProcessingParams();
			documentProcessingParams.getPageProcessingParams().getRecognizerParams().SetPredefinedTextLanguage("ChinesePRC,English");
			documentProcessingParams.getPageProcessingParams().getRecognizerParams().setLowResolutionMode(true);
			documentProcessingParams.getPageProcessingParams().getObjectsExtractionParams().setEnableAggressiveTextExtraction(true);
			
            for (int i = 0; i < pageCount; i++){
                int newResolution = document.getPages().getElement(i).DetectResolution(documentProcessingParams.getPageProcessingParams().getObjectsExtractionParams(), 150, 600);
                if (newResolution > 0){
                	document.getPages().getElement(i).getImageDocument().ChangeResolution(newResolution);
                }
            }
            
			IPageAnalysisParams pageAnalysisParams = engine.CreatePageAnalysisParams();
			pageAnalysisParams.setAggressiveTableDetection(false);
			pageAnalysisParams.setCollectPdfExportData(false);
			pageAnalysisParams.setDetectBarcodes(false);
			pageAnalysisParams.setDetectMultipleBusinessCards(false);
			pageAnalysisParams.setDetectTables(false);
			pageAnalysisParams.setProhibitModelAnalysis(false);
			pageAnalysisParams.setProhibitDoublePageMode(true);
			document.Analyze(pageAnalysisParams, null, null);
            
			// Process document
			document.Process(documentProcessingParams);
			
			result = document.getPlainText().getText();
		}catch(Exception ex) {
			System.out.println(ex.getStackTrace());
		}finally {
			document.Close(); // Close document
		}
		
		return result;
	}
    	
	/**
	 * 取得识别区域的坐标
	 */
	@SuppressWarnings("unused")
	private List<int[]> getCoordinate(){
		List<int[]> rectArray = new ArrayList<int[]>();
		
		int[] rect1 = { 1, 1, 1500, 1500 };
		rectArray.add(rect1);
		
		return rectArray;
	}
	
	/**
	 * 取得识别区域的坐标
	 */
    private List<int[]> getCoordinate(IFRPage page){
        List<int[]> rectArray = new ArrayList<int[]>();
        int w = page.getImageDocument().getColorImage().getWidth();
        int h = page.getImageDocument().getColorImage().getHeight();

        int[] rect1 = { 0, 0, w, h};
        rectArray.add(rect1);

        return rectArray;
    }
    
    /**
     * 自定义语言
     */
    private ITextLanguage createTextLanguage(IEngine engine, String[] langs){
    	ITextLanguage textLanguages = null;
        String recognitionLanguages = "";
        for (int index = 0; index < langs.length; index++){
            if (StringUtils.isNotBlank(recognitionLanguages)){
                recognitionLanguages += ",";
            }
            recognitionLanguages += langs[index];
        }
        ILanguageDatabase langDatabase = engine.CreateLanguageDatabase();
        textLanguages = langDatabase.CreateCompoundTextLanguage(recognitionLanguages);
        return textLanguages;
    }

    /**
     * 自定义识别区域
     */
    private int createRecognitionBlock(ILayout layout, List<int[]> coordinate){
        ITextLanguage textLanguage = null;
        textLanguage = engine.getPredefinedLanguages().Find("English").getTextLanguage();
        
        String[] languages = { "ChinesePRC", "English" };//"English", "ChinesePRC", "Japanese" 
        textLanguage = createTextLanguage(engine, languages);
        //DocumentProcessingParams processingParams = engineLoader.Engine.CreateDocumentProcessingParams();
        //textLanguage = processingParams.PageProcessingParams.RecognizerParams.SetPredefinedTextLanguage("English,ChinesePRC");
        
        int result = 0;
        for (int i = 0; i < coordinate.size(); i++){
            int[] rect = coordinate.get(i);
            if (rect != null && rect.length == 4){
                IRegion region = engine.CreateRegion();
                region.AddRect(rect[0], rect[1], rect[2], rect[3]);
                
                IBlock block = layout.getBlocks().AddNew(BlockTypeEnum.BT_Text, region, i);
                ITextBlock textBlock = block.GetAsTextBlock();
                textBlock.getRecognizerParams().setTextLanguage(textLanguage);
                
                result++;
            }
        }
        return result;
    }
	
	/**
	 * Unload ABBYY FineReader Engine
	 */
	public void unloadEngine() {
		engine = null;
		System.gc();
		System.runFinalization();
		Engine.Unload();
	}
}