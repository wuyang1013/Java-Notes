import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

public class MarkdownMerger {
    private static final String CUSTOM_HEADER = "# 项目文档\n\n正在完善中~~~敬请期待！\n\n";
    private static final String SUMMARY_FILENAME = "00、汇总.md";
    private static final String README_FILENAME = "README.md";
    
    // 获取项目根目录的路径
    private static final Path ROOT_DIR = Paths.get("..").toAbsolutePath().normalize();
    
    // 标题类，用于存储标题的级别和文本
    static class Heading {
        int level;
        String text;
        String anchor;
        
        Heading(int level, String text) {
            this.level = level;
            this.text = text;
            this.anchor = generateAnchor(text);
        }
        
        // 生成标题的锚点链接
        private String generateAnchor(String text) {
            return text.toLowerCase()
                    .replaceAll("[^a-z0-9\\u4e00-\\u9fa5]+", "-")
                    .replaceAll("^-|-$", "");
        }
    }
    
    public static void main(String[] args) {
        try {
            // 1. 获取项目根目录下所有的.md文件（除了汇总文件和README）
            List<Path> mdFiles = Files.list(ROOT_DIR)
                .filter(path -> {
                    String filename = path.getFileName().toString();
                    return filename.endsWith(".md") && 
                           !filename.equals(SUMMARY_FILENAME) && 
                           !filename.equals(README_FILENAME);
                })
                .sorted((p1, p2) -> {
                    // 按照文件名中的数字排序
                    String n1 = extractNumber(p1.getFileName().toString());
                    String n2 = extractNumber(p2.getFileName().toString());
                    return Integer.compare(Integer.parseInt(n1), Integer.parseInt(n2));
                })
                .collect(Collectors.toList());
            
            if (mdFiles.isEmpty()) {
                System.out.println("未找到任何Markdown文件");
                return;
            }
            
            // 2. 读取所有文件内容并生成目录
            List<String> basicTocLines = new ArrayList<>();
            List<String> detailedTocLines = new ArrayList<>();
            StringBuilder summaryContent = new StringBuilder();
            summaryContent.append("# 文档汇总\n\n");
            
            // 存储每个文件的标题结构
            Map<String, List<Heading>> fileHeadings = new LinkedHashMap<>();
            
            for (Path file : mdFiles) {
                String filename = file.getFileName().toString();
                System.out.println("处理文件: " + filename);
                
                // 读取文件内容
                String content = Files.readString(file);
                
                // 提取文件标题（假设第一行是标题）
                String title = extractTitle(content, filename);
                
                // 添加到基本目录
                basicTocLines.add(String.format("- [%s](%s)", title, filename));
                
                // 提取所有标题
                List<Heading> headings = extractAllHeadings(content);
                fileHeadings.put(filename, headings);
                
                // 添加到汇总文档
                summaryContent.append("## ").append(title).append("\n\n");
                summaryContent.append(content).append("\n\n");
            }
            
            // 3. 生成详细目录
            for (Map.Entry<String, List<Heading>> entry : fileHeadings.entrySet()) {
                String filename = entry.getKey();
                List<Heading> headings = entry.getValue();
                
                // 添加文件标题
                String fileTitle = headings.isEmpty() ? filename : headings.get(0).text;
                detailedTocLines.add(String.format("- [%s](%s)", fileTitle, filename));
                
                // 添加文件内的所有标题
                for (Heading heading : headings) {
                    if (heading.level > 1) { // 跳过文件主标题（已经在上面添加了）
                        String indent = "  ".repeat(heading.level - 1);
                        detailedTocLines.add(String.format("%s- [%s](%s#%s)", 
                                indent, heading.text, filename, heading.anchor));
                    }
                }
            }
            
            // 4. 写入汇总文档到项目根目录
            Path summaryPath = ROOT_DIR.resolve(SUMMARY_FILENAME);
            Files.write(summaryPath, 
                       summaryContent.toString().getBytes(), 
                       StandardOpenOption.CREATE, 
                       StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("已生成汇总文档: " + summaryPath);
            
            // 5. 生成README.md到项目根目录
            StringBuilder readmeContent = new StringBuilder();
            readmeContent.append(CUSTOM_HEADER);
            
            // 添加基本目录
            readmeContent.append("## 基本目录\n\n");
            for (String tocLine : basicTocLines) {
                readmeContent.append(tocLine).append("\n");
            }
            
            // 添加详细目录
            readmeContent.append("\n## 详细目录\n\n");
            for (String tocLine : detailedTocLines) {
                readmeContent.append(tocLine).append("\n");
            }
            
            Path readmePath = ROOT_DIR.resolve(README_FILENAME);
            Files.write(readmePath, 
                       readmeContent.toString().getBytes(), 
                       StandardOpenOption.CREATE, 
                       StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("已生成README文档: " + readmePath);
            
        } catch (Exception e) {
            System.err.println("处理过程中出现错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 从文件名中提取数字部分
    private static String extractNumber(String filename) {
        Matcher matcher = Pattern.compile("^(\\d+)").matcher(filename);
        return matcher.find() ? matcher.group(1) : "999";
    }
    
    // 提取文件的主标题
    private static String extractTitle(String content, String filename) {
        // 尝试从内容中提取标题（寻找第一个#开头的行）
        Pattern pattern = Pattern.compile("^#\\s+(.+)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        if (matcher.find()) {
            return matcher.group(1);
        }
        
        // 如果没有找到标题，使用文件名（去掉.md后缀和前面的数字）
        return filename.replaceAll("^\\d+、", "").replace(".md", "");
    }
    
    // 提取文档中的所有标题
    private static List<Heading> extractAllHeadings(String content) {
        List<Heading> headings = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(#+)\\s+(.+)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);
        
        while (matcher.find()) {
            int level = matcher.group(1).length(); // #的数量表示级别
            String text = matcher.group(2);
            headings.add(new Heading(level, text));
        }
        
        return headings;
    }
}