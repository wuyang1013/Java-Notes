private JTextField startNumberField;

public FileRenamer() {
    setTitle("文件重命名工具 - 处理主目录文件");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 550);
    setLocationRelativeTo(null);

    // 获取程序所在目录和主目录（上一级目录）
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
    startNumberField = new JTextField("0", 3); // 修改默认值为0
    startNumberPanel.add(startNumberField);
    
    renameButton = new JButton("开始重命名");
    
    buttonPanel.add(refreshButton);
    buttonPanel.add(ignoreButton);
    buttonPanel.add(startNumberPanel);
    buttonPanel.add(renameButton);
    
    JPanel topContainer = new JPanel(new BorderLayout());
    topContainer.add(topPanel, BorderLayout.NORTH);
    topContainer.add(buttonPanel, BorderLayout.SOUTH);
    
    mainPanel.add(topContainer, BorderLayout.NORTH);

}