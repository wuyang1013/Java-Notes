# Git篇

### 1. 配置与初始化

这些命令用于设置 Git 环境和创建新的仓库。

| 命令                                           | 解释                              | 示例                                                  |
| :--------------------------------------------- | :-------------------------------- | :---------------------------------------------------- |
| `git config --global user.name "[name]"`     | 设置全局用户名                    | `git config --global user.name "John Doe"`          |
| `git config --global user.email "[email]"`   | 设置全局邮箱地址                  | `git config --global user.email "john@example.com"` |
| `git config --global core.editor "[editor]"` | 设置默认文本编辑器                | `git config --global core.editor "code --wait"`     |
| `git init [project-name]`                    | 在当前目录初始化一个新的 Git 仓库 | `git init` 或 `git init my-project`               |
| `git clone [url]`                            | 克隆（下载）一个远程仓库到本地    | `git clone https://github.com/user/repo.git`        |

### 2. 基础快照操作

这些是日常开发中最常用的命令，用于跟踪文件的变更。

#### 2.1 查看状态与差异

| 命令                                    | 解释                                               | 示例                         |
| :-------------------------------------- | :------------------------------------------------- | :--------------------------- |
| `git status`                          | 显示工作目录和暂存区的状态（已修改、未跟踪的文件） | `git status`               |
| `git status -s`                       | 以精简模式显示状态                                 | `git status -s`            |
| `git diff`                            | **未暂存**的更改与最后一次提交的比较         | `git diff`                 |
| `git diff --staged` (或 `--cached`) | **已暂存**的更改与最后一次提交的比较         | `git diff --staged`        |
| `git diff [commit1] [commit2]`        | 比较两次提交之间的差异                             | `git diff a1b2c3d e4f5g6h` |

#### 2.2 添加与提交

| 命令                             | 解释                                                     | 示例                                    |
| :------------------------------- | :------------------------------------------------------- | :-------------------------------------- |
| `git add [file]`               | 将**指定文件**的更改添加到暂存区                   | `git add index.html`                  |
| `git add .`                    | 将**所有更改**（包括修改和新文件）添加到暂存区     | `git add .`                           |
| `git add -A`                   | 添加所有更改（包括修改、新文件和已删除的文件）           | `git add -A`                          |
| `git add -p`                   | **交互式**地选择要暂存的更改（块）                 | `git add -p`                          |
| `git commit -m "[message]"`    | 提交已暂存的更改，并附上提交信息                         | `git commit -m "Fix login bug"`       |
| `git commit -a -m "[message]"` | 跳过 `git add`，直接提交所有**已跟踪文件**的更改 | `git commit -a -m "Update styles"`    |
| `git commit --amend`           | **修补**最后一次提交（修改信息或加入漏掉的文件）   | `git commit --amend -m "New message"` |

#### 3. 分支与合并

分支是 Git 的核心功能，用于并行开发和管理不同功能线。

| 命令                              | 解释                                                         | 示例                                          |
| :-------------------------------- | :----------------------------------------------------------- | :-------------------------------------------- |
| `git branch`                    | 列出所有本地分支（当前分支前有 `*` 号）                    | `git branch`                                |
| `git branch -a`                 | 列出所有本地和远程分支                                       | `git branch -a`                             |
| `git branch [branch-name]`      | 创建一个新分支                                               | `git branch feature-x`                      |
| `git checkout [branch-name]`    | 切换到指定分支                                               | `git checkout main`                         |
| `git switch [branch-name]`      | (Git 2.23+) 更清晰的切换分支命令                             | `git switch main`                           |
| `git checkout -b [branch-name]` | **创建并切换**到新分支                                 | `git checkout -b hotfix`                    |
| `git switch -c [branch-name]`   | (Git 2.23+)**创建并切换**到新分支                      | `git switch -c hotfix`                      |
| `git merge [branch]`            | 将指定分支的历史合并到**当前分支**                     | `git switch main` + `git merge feature-x` |
| `git branch -d [branch-name]`   | **删除**指定的分支（已合并的分支）                     | `git branch -d feature-x`                   |
| `git branch -D [branch-name]`   | **强制删除**指定的分支（即使未合并）                   | `git branch -D experimental`                |
| `git rebase [base-branch]`      | 将当前分支的更改在目标分支上**重演**，创造更线性的历史 | `git switch feature` + `git rebase main`  |

#### 4. 查看历史与日志

用于查看项目的提交历史。

| 命令                          | 解释                            | 示例                          |
| :---------------------------- | :------------------------------ | :---------------------------- |
| `git log`                   | 按时间倒序列出提交历史          | `git log`                   |
| `git log --oneline`         | 以精简的单行模式显示历史        | `git log --oneline`         |
| `git log -p`                | 显示每次提交的详细差异（patch） | `git log -p`                |
| `git log -n [limit]`        | 限制显示提交的数量              | `git log -n 5`              |
| `git log --graph --oneline` | 以 ASCII 图形显示分支合并历史   | `git log --graph --oneline` |
| `git show [commit]`         | 显示某次提交的元数据和内容变化  | `git show a1b2c3d`          |
| `git blame [file]`          | 逐行显示文件最后的修改者和提交  | `git blame README.md`       |

#### 5. 撤销与回退

用于撤销更改或回退到之前的某个状态。

| 命令                                           | 解释                                                  | 应用场景                                    |
| :--------------------------------------------- | :---------------------------------------------------- | :------------------------------------------ |
| `git restore [file]`                         | **丢弃工作区**中指定文件的更改（未 `add` 的） | 改乱了文件，想恢复到上次提交的样子          |
| `git restore --staged [file]`                | 将文件从**暂存区**移回工作区（取消 `add`）    | `add` 了不该 add 的文件                   |
| `git reset [--soft/--mixed/--hard] [commit]` | **回退**到指定的提交                            | **慎用 `--hard`，会丢失工作区更改** |
| `git revert [commit]`                        | 创建一个**新的提交**来撤销指定提交的更改        | 安全地撤销已推送到远程的提交                |

**`git reset` 模式详解：**

- `--soft`：回退到某个版本，但**保留工作区和暂存区**的内容。
- `--mixed` (**默认**)：回退到某个版本，**保留工作区**，但重置暂存区。
- `--hard`：**彻底**回退到某个版本，丢弃工作区和暂存区的所有更改。

#### 6. 远程协作

用于与远程仓库（如 GitHub, GitLab）进行交互。

| 命令                                    | 解释                                                           | 示例                                    |
| :-------------------------------------- | :------------------------------------------------------------- | :-------------------------------------- |
| `git remote -v`                       | 列出所有已配置的远程仓库及其 URL                               | `git remote -v`                       |
| `git remote add [name] [url]`         | 添加一个新的远程仓库                                           | `git remote add origin https://...`   |
| `git fetch [remote] [branch]`         | **下载**远程仓库的变更到本地，但**不合并**         | `git fetch origin main`               |
| `git pull [remote] [branch]`          | **下载并合并**远程分支到当前分支 (`fetch` + `merge`) | `git pull origin main`                |
| `git pull --rebase [remote] [branch]` | 下载并以 `rebase` 方式合并                                   | `git pull --rebase origin main`       |
| `git push [remote] [branch]`          | 将本地分支推送到远程仓库                                       | `git push origin feature-x`           |
| `git push -u origin [branch]`         | 推送并建立本地分支与远程分支的**跟踪关系**               | `git push -u origin feature-x`        |
| `git push --force-with-lease`         | **安全强制推送**（比 `--force` 更安全）                | 在 rebase 后推送时使用                  |
| `git push [remote] --delete [branch]` | 删除远程分支                                                   | `git push origin --delete old-branch` |

#### 7. 储藏（Stashing）

临时保存工作目录的修改，以便处理其他事情。

| 命令                            | 解释                                       | 示例                                    |
| :------------------------------ | :----------------------------------------- | :-------------------------------------- |
| `git stash`                   | 将当前工作区的修改储藏起来                 | `git stash`                           |
| `git stash save "[message]"`  | 储藏并添加说明信息                         | `git stash save "WIP: login feature"` |
| `git stash list`              | 列出所有储藏                               | `git stash list`                      |
| `git stash pop`               | 应用最新的储藏并**将其从堆栈中删除** | `git stash pop`                       |
| `git stash apply [stash@{n}]` | 应用指定的储藏，但**不删除**它       | `git stash apply stash@{0}`           |
| `git stash drop [stash@{n}]`  | 删除指定的储藏                             | `git stash drop stash@{0}`            |
| `git stash clear`             | 清空所有储藏                               | `git stash clear`                     |

#### 8. 标签（Tagging）

为特定的提交打上标记，通常用于发布版本。

| 命令                                        | 解释                       | 示例                                             |
| :------------------------------------------ | :------------------------- | :----------------------------------------------- |
| `git tag`                                 | 列出所有标签               | `git tag`                                      |
| `git tag -a [tag-name] -m "[message]"`    | 创建一个带注解的标签       | `git tag -a v1.0.0 -m "Release version 1.0.0"` |
| `git tag [tag-name]`                      | 创建一个轻量标签           | `git tag v1.0.1`                               |
| `git show [tag-name]`                     | 显示标签的详细信息         | `git show v1.0.0`                              |
| `git push [remote] [tag-name]`            | 推送单个标签到远程仓库     | `git push origin v1.0.0`                       |
| `git push [remote] --tags`                | 推送所有本地标签到远程仓库 | `git push origin --tags`                       |
| `git tag -d [tag-name]`                   | 删除本地标签               | `git tag -d v1.0.1`                            |
| `git push [remote] :refs/tags/[tag-name]` | 删除远程标签               | `git push origin :refs/tags/v1.0.1`            |

#### 总结

一个典型的 Git 工作流如下：

1. **准备**：`git status` -> `git diff` (查看更改)
2. **暂存**：`git add .` 或 `git add -p` (选择要提交的更改)
3. **提交**：`git commit -m "message"` (创建提交)
4. **同步**：`git pull --rebase origin main` (获取远程最新代码并变基)
5. **推送**：`git push origin feature-branch` (将本地提交推送到远程)
