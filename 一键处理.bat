@echo off
chcp 65001 >nul
setlocal

echo 正在编译和运行Java程序...

cd /d "%~dp0scripts" 2>nul || (
    echo 错误：找不到scripts目录
    pause
    exit /b 1
)

if not exist "FileRenamer.java" (
    echo 错误：找不到FileRenamer.java
    pause
    exit /b 1
)

if not exist "MarkdownMerger.java" (
    echo 错误：找不到MarkdownMerger.java
    pause
    exit /b 1
)

echo 编译MarkdownMerger.java...
javac -encoding UTF-8 MarkdownMerger.java
if errorlevel 1 (
    echo 编译失败
    pause
    exit /b 1
)

echo 运行MarkdownMerger程序...
java MarkdownMerger

echo 编译FileRenamer.java...
javac -encoding UTF-8 FileRenamer.java
if errorlevel 1 (
    echo 编译失败
    pause
    exit /b 1
)

echo 运行FileRenamer程序...
java FileRenamer

echo 程序执行完成
pause