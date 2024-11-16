import java.io.File;
import java.util.Scanner;

/**
 * 瑞克论坛中 去除水标文件名
 */
public class BatchRenameTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入要修改的根目录路径
        String rootPath = "E:\\BaiduNetdiskDownload\\rk0294-《尼恩Java硬核架构视频》史上最为硬核的Java架构班，来自40岁一线老架构师尼恩";

        // 输入要去除的字符
        String charToRemove = "【瑞客论坛 www.ruike1.com】";

        File rootDirectory = new File(rootPath);

        if (rootDirectory.exists() && rootDirectory.isDirectory()) {
            modifyNamesRecursively(rootDirectory, charToRemove);
            System.out.println("批量修改完成！");
        } else {
            System.out.println("输入的路径无效或不是文件夹！");
        }

        scanner.close();
    }

    /**
     * 递归地修改目录下所有文件的名称
     *
     * @param file 文件或文件夹对象
     * @param charToRemove 要从文件名中去除的字符
     */
    private static void modifyNamesRecursively(File file, String charToRemove) {
        // 处理文件夹中的文件和子文件夹
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                modifyNamesRecursively(subFile, charToRemove);
            }
        }

        // 仅对文件进行处理（跳过文件夹）
        if (file.isFile()) {
            String fileName = file.getName();
            int dotIndex = fileName.lastIndexOf(".");
            String fileExtension = dotIndex != -1 ? fileName.substring(dotIndex) : "";

            // 去掉文件扩展名后的部分
            String baseName = dotIndex != -1 ? fileName.substring(0, dotIndex) : fileName;
            String newName = baseName.replace(charToRemove, "") + fileExtension;

            if (!newName.equals(fileName)) {
                File renamedFile = new File(file.getParent(), newName);

                if (file.renameTo(renamedFile)) {
                    System.out.println("已将 " + file.getPath() + " 修改为 " + renamedFile.getPath());
                } else {
                    System.out.println("修改 " + file.getPath() + " 时出错！");
                }
            } else {
                System.out.println("文件名未发生变化：" + file.getPath());
            }
        }
    }
}
