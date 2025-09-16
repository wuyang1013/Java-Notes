import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRenamer extends JFrame {
    private DefaultListModel<FileItem> listModel;
    private JList<FileItem> fileList;
    private JButton renameButton;
    private JButton refreshButton;
    private JButton ignoreButton;
    // private JButton mergeButton;
    private JTextField startNumberField;
    private JTextArea logArea;
    private File mainDirectory;  // 主目录（文档所在目录）
    private File appDirectory;   // 程序所在目录

    public FileRenamer() {
        setTitle("文件重命名工具 - 处理主目录文件");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        // 获取程序所在目录和主目录（上一级目录）
        // 修复路径问题：使用System.getProperty("user.dir")而不是new File(".")
        appDirectory = new File(System.getProperty("user.dir"));
        mainDirectory = appDirectory.getParentFile();
        
        if (mainDirectory == null || !mainDirectory.exists()) {
            JOptionPane.showMessageDialog(this, 
                "无法访问主目录，请将程序放在合适的子文件夹中", 
                "错误", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 创建顶部面板
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(new JLabel("文档目录: " + mainDirectory.getAbsolutePath()));
        topPanel.add(new JLabel("程序目录: " + appDirectory.getAbsolutePath()));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        refreshButton = new JButton("刷新列表");
        ignoreButton = new JButton("忽略选中");
        
        // 添加起始序号输入框
        JPanel startNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startNumberPanel.add(new JLabel("起始序号:"));
        startNumberField = new JTextField("0", 3);
        startNumberPanel.add(startNumberField);
        
        renameButton = new JButton("开始重命名");
        // mergeButton = new JButton("一键生成汇总目录");
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(ignoreButton);
        buttonPanel.add(startNumberPanel);
        buttonPanel.add(renameButton);
        // buttonPanel.add(mergeButton);
        
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(topPanel, BorderLayout.NORTH);
        topContainer.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(topContainer, BorderLayout.NORTH);

        // 创建文件列表和操作面板
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        // 创建文件列表
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        fileList.setCellRenderer(new FileItemRenderer());
        fileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        // 启用拖拽排序
        fileList.setDragEnabled(true);
        fileList.setDropMode(DropMode.INSERT);
        fileList.setTransferHandler(new ListItemTransferHandler());
        
        JScrollPane listScrollPane = new JScrollPane(fileList);
        listScrollPane.setPreferredSize(new Dimension(400, 300));
        
        // 创建操作说明面板
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        instructionPanel.setBorder(BorderFactory.createTitledBorder("操作说明"));
        
        JLabel instruction1 = new JLabel("1. 通过拖拽调整文件顺序");
        JLabel instruction2 = new JLabel("2. 选择文件后点击'忽略选中'按钮");
        JLabel instruction3 = new JLabel("3. 设置起始序号(默认1)");
        JLabel instruction4 = new JLabel("4. 点击'开始重命名'执行操作");
        JLabel instruction5 = new JLabel("5. 点击'一键生成汇总目录'合并文件");
        
        instructionPanel.add(instruction1);
        instructionPanel.add(instruction2);
        instructionPanel.add(instruction3);
        instructionPanel.add(instruction4);
        instructionPanel.add(instruction5);
        
        centerPanel.add(listScrollPane, BorderLayout.CENTER);
        centerPanel.add(instructionPanel, BorderLayout.EAST);

        // 创建日志区域
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setPreferredSize(new Dimension(400, 150));

        // 添加组件到主面板
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(logScrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // 添加事件监听器
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFileList();
            }
        });
        
        ignoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleIgnoreFiles();
            }
        });
        
        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renameFiles();
            }
        });
        
        // mergeButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         runMarkdownMerger();
        //     }
        // });
        
        // 初始显示目录中的文件
        refreshFileList();
    }

    private void refreshFileList() {
        listModel.clear();
        File[] files = mainDirectory.listFiles();
        
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".md")) {
                    listModel.addElement(new FileItem(file, false));
                }
            }
        }
        
        logArea.setText("已加载 " + listModel.size() + " 个.md文件\n");
        logArea.append("请通过拖拽调整文件顺序，选择文件后点击'忽略选中'按钮\n");
    }
    
    private void toggleIgnoreFiles() {
        int[] selectedIndices = fileList.getSelectedIndices();
        if (selectedIndices.length == 0) {
            JOptionPane.showMessageDialog(this, "请先选择文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // 切换选中文件的忽略状态
        for (int index : selectedIndices) {
            FileItem item = listModel.getElementAt(index);
            item.setIgnored(!item.isIgnored());
        }
        
        // 更新列表显示
        fileList.repaint();
        
        // 显示操作结果
        logArea.append("已切换 " + selectedIndices.length + " 个文件的忽略状态\n");
    }

    private void renameFiles() {
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "没有可重命名的文件", "信息", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // 获取起始序号
        int startNumber = 0;
        try {
            startNumber = Integer.parseInt(startNumberField.getText().trim());
            if (startNumber < 0) {
                JOptionPane.showMessageDialog(this, "起始序号不能为负数", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的起始序号", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int successCount = 0;
        int failCount = 0;
        int ignoreCount = 0;
        
        logArea.setText("开始重命名操作 (起始序号: " + startNumber + "):\n");
        
        // 获取排序后的文件列表
        List<FileItem> fileItems = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            fileItems.add(listModel.getElementAt(i));
        }
        
        // 重命名文件
        for (int i = 0; i < fileItems.size(); i++) {
            FileItem item = fileItems.get(i);
            
            if (item.isIgnored()) {
                logArea.append("忽略: " + item.getFile().getName() + "\n");
                ignoreCount++;
                continue;
            }
            
            File originalFile = item.getFile();
            String originalName = originalFile.getName();
            
            // 提取原文件名中的内容部分（去掉序号部分）
            String contentPart = extractContentPart(originalName);
            
            // 生成新文件名
            String newNumber = String.format("%02d", startNumber + i); // 两位数字序号，从起始序号开始
            String newName = newNumber + "、" + contentPart;
            
            if (originalName.equals(newName)) {
                logArea.append("跳过: " + originalName + " (无需更改)\n");
                successCount++;
                continue;
            }
            
            File newFile = new File(originalFile.getParent(), newName);
            
            // 检查新文件名是否已存在
            if (newFile.exists() && !newFile.equals(originalFile)) {
                logArea.append("失败: " + originalName + " -> " + newName + " (目标文件已存在)\n");
                failCount++;
                continue;
            }
            
            // 执行重命名
            if (originalFile.renameTo(newFile)) {
                logArea.append("成功: " + originalName + " -> " + newName + "\n");
                successCount++;
                // 更新文件引用
                item.setFile(newFile);
            } else {
                logArea.append("失败: " + originalName + " -> " + newName + "\n");
                failCount++;
            }
        }
        
        logArea.append("\n重命名完成! 成功: " + successCount + ", 失败: " + failCount + ", 忽略: " + ignoreCount + "\n");
        
        if (failCount == 0) {
            JOptionPane.showMessageDialog(this, "文件重命名成功!", "完成", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "重命名完成，但有 " + failCount + " 个文件失败", 
                "完成", JOptionPane.WARNING_MESSAGE);
        }
        
        // 刷新列表显示新文件名
        fileList.repaint();
    }
    
    // private void runMarkdownMerger() {
    //     // 检查MarkdownMerger程序是否存在
    //     File mergerFile = new File(appDirectory, "MarkdownMerger.class");
    //     if (!mergerFile.exists()) {
    //         mergerFile = new File(appDirectory, "MarkdownMerger.jar");
    //         if (!mergerFile.exists()) {
    //             JOptionPane.showMessageDialog(this, 
    //                 "找不到MarkdownMerger程序，请确保MarkdownMerger.class或MarkdownMerger.jar在同一目录下", 
    //                 "错误", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }
    //     }
        
    //     // 将mergerFile转换为final变量以便在内部类中使用
    //     final File finalMergerFile = mergerFile;
        
    //     // 在新线程中运行MarkdownMerger，避免界面卡顿
    //     new Thread(new Runnable() {
    //         @Override
    //         public void run() {
    //             try {
    //                 Process process;
    //                 if (finalMergerFile.getName().endsWith(".class")) {
    //                     // 运行.class文件，工作目录设置为文档目录
    //                     process = Runtime.getRuntime().exec("java MarkdownMerger", null, mainDirectory);
    //                 } else {
    //                     // 运行.jar文件，工作目录设置为文档目录
    //                     process = Runtime.getRuntime().exec("java -jar MarkdownMerger.jar", null, mainDirectory);
    //                 }
                    
    //                 // 读取输出流
    //                 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    //                 final StringBuilder output = new StringBuilder();
                    
    //                 // 使用局部变量而不是外部变量
    //                 String outputLine;
    //                 while ((outputLine = reader.readLine()) != null) {
    //                     output.append(outputLine).append("\n");
    //                 }
                    
    //                 // 等待进程结束
    //                 int exitCode = process.waitFor();
                    
    //                 // 在UI线程中更新日志
    //                 SwingUtilities.invokeLater(new Runnable() {
    //                     @Override
    //                     public void run() {
    //                         if (exitCode == 0) {
    //                             logArea.append("MarkdownMerger执行成功!\n");
    //                             if (output.length() > 0) {
    //                                 logArea.append("输出: " + output.toString() + "\n");
    //                             }
    //                         } else {
    //                             logArea.append("MarkdownMerger执行失败，退出代码: " + exitCode + "\n");
    //                             if (output.length() > 0) {
    //                                 logArea.append("输出: " + output.toString() + "\n");
    //                             }
                                
    //                             // 尝试读取错误流
    //                             try {
    //                                 BufferedReader errorReader = new BufferedReader(
    //                                     new InputStreamReader(process.getErrorStream()));
    //                                 StringBuilder errorOutput = new StringBuilder();
    //                                 String errorLine; // 使用局部变量
    //                                 while ((errorLine = errorReader.readLine()) != null) {
    //                                     errorOutput.append(errorLine).append("\n");
    //                                 }
    //                                 if (errorOutput.length() > 0) {
    //                                     logArea.append("错误信息: " + errorOutput.toString() + "\n");
    //                                 }
    //                             } catch (Exception ex) {
    //                                 logArea.append("无法读取错误信息: " + ex.getMessage() + "\n");
    //                             }
    //                         }
    //                     }
    //                 });
                    
    //             } catch (Exception ex) {
    //                 SwingUtilities.invokeLater(new Runnable() {
    //                     @Override
    //                     public void run() {
    //                         logArea.append("执行MarkdownMerger时出错: " + ex.getMessage() + "\n");
    //                     }
    //                 });
    //             }
    //         }
    //     }).start();
        
    //     logArea.append("正在运行MarkdownMerger，请稍候...\n");
    // }
    
    private String extractContentPart(String fileName) {
        // 尝试匹配 "数字、内容.md" 格式
        Pattern pattern = Pattern.compile("^\\d+、(.*)\\.md$");
        Matcher matcher = pattern.matcher(fileName);
        
        if (matcher.find()) {
            return matcher.group(1) + ".md";
        }
        
        // 如果不是标准格式，直接返回原文件名
        return fileName;
    }

    // 文件项类
    class FileItem {
        private File file;
        private boolean ignored;
        
        public FileItem(File file, boolean ignored) {
            this.file = file;
            this.ignored = ignored;
        }
        
        public File getFile() {
            return file;
        }
        
        public void setFile(File file) {
            this.file = file;
        }
        
        public boolean isIgnored() {
            return ignored;
        }
        
        public void setIgnored(boolean ignored) {
            this.ignored = ignored;
        }
        
        @Override
        public String toString() {
            return file.getName() + (ignored ? " [忽略]" : "");
        }
    }
    
    // 自定义列表项渲染器
    class FileItemRenderer extends JLabel implements ListCellRenderer<FileItem> {
        public FileItemRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getListCellRendererComponent(JList<? extends FileItem> list, FileItem value, 
                                                     int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.toString());
            
            if (value.isIgnored()) {
                setForeground(Color.GRAY);
                setFont(getFont().deriveFont(Font.ITALIC));
            } else {
                setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
                setFont(list.getFont());
            }
            
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            
            return this;
        }
    }
    
    // 拖拽排序处理器 - 已修复
    class ListItemTransferHandler extends TransferHandler {
        private int[] indices;
        private int addIndex = -1;
        private int addCount;
        
        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }
        
        @Override
        protected Transferable createTransferable(JComponent c) {
            JList<?> list = (JList<?>) c;
            indices = list.getSelectedIndices();
            
            List<FileItem> selectedValues = new ArrayList<>();
            for (int i : indices) {
                selectedValues.add(listModel.getElementAt(i));
            }
            
            return new FileItemTransferable(selectedValues);
        }
        
        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE && indices != null) {
                // 修复：只有在没有成功导入数据时才删除原位置的项
                // 实际删除操作将在importData中处理
                if (addIndex == -1) {
                    for (int i = indices.length - 1; i >= 0; i--) {
                        listModel.remove(indices[i]);
                    }
                }
            }
            
            indices = null;
            addIndex = -1;
            addCount = 0;
        }
        
        @Override
        public boolean canImport(TransferHandler.TransferSupport info) {
            return info.isDataFlavorSupported(FileItemTransferable.FILE_ITEM_FLAVOR);
        }
        
        @Override
        public boolean importData(TransferHandler.TransferSupport info) {
            if (!info.isDrop()) {
                return false;
            }
            
            JList.DropLocation dl = (JList.DropLocation) info.getDropLocation();
            addIndex = dl.getIndex();
            boolean isInsert = dl.isInsert();
            
            try {
                @SuppressWarnings("unchecked")
                List<FileItem> items = (List<FileItem>) info.getTransferable()
                        .getTransferData(FileItemTransferable.FILE_ITEM_FLAVOR);
                
                addCount = items.size();
                
                // 修复：检查是否拖拽到自身位置
                if (indices != null && indices.length > 0) {
                    int firstIndex = indices[0];
                    if (isInsert && addIndex > firstIndex && addIndex <= firstIndex + indices.length) {
                        // 拖拽到自身范围内，不执行任何操作
                        addIndex = -1;
                        return false;
                    }
                }
                
                // 修复：先删除原位置的项
                if (indices != null) {
                    // 从大到小删除，避免索引变化
                    int[] sortedIndices = indices.clone();
                    Arrays.sort(sortedIndices);
                    for (int i = sortedIndices.length - 1; i >= 0; i--) {
                        int index = sortedIndices[i];
                        // 检查删除的项是否在插入点之前，如果是则需要调整插入点
                        if (index < addIndex) {
                            addIndex--;
                        }
                        listModel.remove(index);
                    }
                }
                
                // 插入到新位置
                for (FileItem item : items) {
                    listModel.add(addIndex++, item);
                }
                
                // 选中新插入的项
                int[] newIndices = new int[items.size()];
                for (int i = 0; i < items.size(); i++) {
                    newIndices[i] = addIndex - items.size() + i;
                }
                fileList.setSelectedIndices(newIndices);
                
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return false;
        }
    }
    
    // 可传输的文件项类
    class FileItemTransferable implements Transferable {
        public static final DataFlavor FILE_ITEM_FLAVOR = new DataFlavor(List.class, "FileItemList");
        private List<FileItem> items;
        
        public FileItemTransferable(List<FileItem> items) {
            this.items = items;
        }
        
        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{FILE_ITEM_FLAVOR};
        }
        
        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return FILE_ITEM_FLAVOR.equals(flavor);
        }
        
        @Override
        public Object getTransferData(DataFlavor flavor) {
            if (isDataFlavorSupported(flavor)) {
                return items;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                FileRenamer renamer = new FileRenamer();
                renamer.setVisible(true);
            }
        });
    }
}