package com.facevalid.demo;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.*;
import com.arcsoft.face.toolkit.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.arcsoft.face.toolkit.ImageInfoEx;

import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


public class FaceEngineTest {


    public static void main(String[] args) {

        //从官网获取
        String appId = "3F6citkd8dGpY6181dYAcX8ES2WzVncf8YQ8rfH9gC29";
        String sdkKey = "Ej9UJ4Frg98EZAum1tjmuEbL9yBM4vzerL8JXAhVVsbP";


        FaceEngine faceEngine = new FaceEngine();
        //在线激活SDK
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }


        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();

        /**
         * <ASF_DETECT_MODE_VIDEO></>
         *      1. 对视频流中的人脸进行追踪，人脸框平滑过渡，不会出现跳框的现象。
         *      2. 用于预览帧数据的人脸追踪，使用该模式比使用IMAGE模式处理速度更快，可避免出现卡顿问题。
         *      3. 在视频模式下人脸追踪会带有一个 faceId 值，该值用于标记一张人脸，当一个人脸从进入画面直到离开画面， faceId 值不变，这可用于业务中优化程序性能。
         *
         * <ASF_DETECT_MODE_IMAGE></>
         *      1. 针对单张图片进行人脸检测精度更高。
         *      2. 在注册人脸库时，我们建议使用精度更高的IMAGE模式。
         *
         * <模式选择></>
         *      1. 从摄像头中获取数据并需要预览显示，推荐选择VIDEO模式；
         *      2. 处理静态图像数据，类似注册人脸库时，推荐使用IMAGE模式；
         *      3. 若同时需要进行IMAGE模式人脸检测和VIDEO模式人脸检测，需要创建一个VIDEO模式的引擎和一个IMAGE模式的引擎；
         */
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        //人脸检测角度
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        //最多检测人脸数
        engineConfiguration.setDetectFaceMaxNum(10);
        //VIDEO模式推荐16；IMAGE模式推荐32 (数值越高 距离越远)
        engineConfiguration.setDetectFaceScaleVal(16);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);

        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportGender(true);
        //活体检测
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(false);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }


        //人脸检测
        ImageInfo imageInfo = getRGBData(new File("E:\\compare\\lyh.jpeg"));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();

        //faceInfoList 赋值
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);

        System.out.println(faceInfoList);



        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File("E:\\compare\\lj.jpeg"));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(),imageInfo2.getImageFormat(), faceInfoList2);
        System.out.println(faceInfoList2);

        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
        System.out.println("特征值大小：" + faceFeature2.getFeatureData().length);

        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());

        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());

        FaceSimilar faceSimilar = new FaceSimilar();
        // 特征对比
        errorCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        System.out.println("相似度：" + faceSimilar.getScore());

        //设置活体测试
        errorCode = faceEngine.setLivenessParam(0.5f, 0.7f);
        //人脸属性检测
        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        errorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);


        //性别检测
        List<GenderInfo> genderInfoList = new ArrayList<GenderInfo>();
        errorCode = faceEngine.getGender(genderInfoList);
        System.out.println("性别：" + genderInfoList.get(0).getGender());

        //年龄检测
        List<AgeInfo> ageInfoList = new ArrayList<AgeInfo>();
        errorCode = faceEngine.getAge(ageInfoList);
        System.out.println("年龄：" + ageInfoList.get(0).getAge());

        //3D信息检测
        List<Face3DAngle> face3DAngleList = new ArrayList<Face3DAngle>();
        errorCode = faceEngine.getFace3DAngle(face3DAngleList);
        System.out.println("3D角度：" + face3DAngleList.get(0).getPitch() + "," + face3DAngleList.get(0).getRoll() + "," + face3DAngleList.get(0).getYaw());

        //活体检测
        List<LivenessInfo> livenessInfoList = new ArrayList<LivenessInfo>();
        errorCode = faceEngine.getLiveness(livenessInfoList);
        System.out.println("活体：" + livenessInfoList.get(0).getLiveness());


        //IR属性处理
        ImageInfo imageInfoGray = getGrayData(new File("d:\\IR_480p.jpg"));
        List<FaceInfo> faceInfoListGray = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray);

        FunctionConfiguration configuration2 = new FunctionConfiguration();
        configuration2.setSupportIRLiveness(true);
        errorCode = faceEngine.processIr(imageInfoGray.getImageData(), imageInfoGray.getWidth(), imageInfoGray.getHeight(), imageInfoGray.getImageFormat(), faceInfoListGray, configuration2);
        //IR活体检测
        List<IrLivenessInfo> irLivenessInfo = new ArrayList<>();
        errorCode = faceEngine.getLivenessIr(irLivenessInfo);
        System.out.println("IR活体：" + irLivenessInfo.get(0).getLiveness());

        ImageInfoEx imageInfoEx = new ImageInfoEx();
        imageInfoEx.setHeight(imageInfo.getHeight());
        imageInfoEx.setWidth(imageInfo.getWidth());
        imageInfoEx.setImageFormat(imageInfo.getImageFormat());
        imageInfoEx.setImageDataPlanes(new byte[][]{imageInfo.getImageData()});
        imageInfoEx.setImageStrides(new int[]{imageInfo.getWidth() * 3});
        List<FaceInfo> faceInfoList1 = new ArrayList<>();
        errorCode = faceEngine.detectFaces(imageInfoEx, DetectModel.ASF_DETECT_MODEL_RGB, faceInfoList1);

        FunctionConfiguration fun = new FunctionConfiguration();
        fun.setSupportAge(true);
        errorCode = faceEngine.process(imageInfoEx, faceInfoList1, functionConfiguration);
        List<AgeInfo> ageInfoList1 = new ArrayList<>();
        int age = faceEngine.getAge(ageInfoList1);
        System.out.println("年龄：" + ageInfoList1.get(0).getAge());

        FaceFeature feature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfoEx, faceInfoList1.get(0), feature);


        //引擎卸载
        errorCode = faceEngine.unInit();

    }
}
