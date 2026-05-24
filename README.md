# MusicApp

MusicApp 是一个基于 Kotlin 与 Jetpack Compose 开发的 Android 音乐播放器项目。项目采用多模块架构，将基础能力、数据访问、播放器能力和业务页面拆分到独立模块中，便于维护和扩展。

## 项目简介

本项目实现了一个音乐 App 的核心体验，包括首页推荐、搜索、专辑/歌单详情、歌手详情、音乐播放、迷你播放器、播放队列和歌词展示等功能。网络数据通过 Retrofit + OkHttp 请求远端接口，播放能力基于 AndroidX Media3 实现，并使用 Hilt 进行依赖注入。

## 主要功能

- 首页内容展示：Banner、推荐歌单、最新专辑、排行榜、热门歌手
- 搜索功能：搜索建议、综合结果、歌曲、歌手、歌单、专辑等结果页
- 歌单与专辑详情：展示歌曲列表并支持进入播放
- 歌手详情：展示歌手信息及热门歌曲
- 音乐播放：播放/暂停、进度控制、歌曲详情展示
- 歌词页面：歌词解析、滚动同步、点击歌词跳转播放进度
- 迷你播放器：全局底部播放入口与播放列表弹窗
- 登录/会话能力：通过 DataStore 保存用户 Cookie，并在网络请求中自动携带
- 前台播放服务：支持媒体播放前台服务与通知权限申请

## 技术栈

- 开发语言：Kotlin
- UI 框架：Jetpack Compose、Material 3
- 架构组件：ViewModel、StateFlow、Navigation Compose
- 依赖注入：Hilt
- 网络请求：Retrofit、OkHttp、Gson
- 图片加载：Coil
- 数据存储：DataStore Preferences
- 媒体播放：AndroidX Media3 ExoPlayer、MediaSessionService
- 构建工具：Gradle Kotlin DSL、Version Catalog、自定义 convention plugin

## 项目结构

```text
MusicApp/
├── app/                  # 应用入口、导航、全局播放器 UI
├── core/
│   ├── common/           # 通用工具函数，例如时间格式化
│   ├── data/             # API、数据模型、Repository、Hilt 网络模块、用户会话管理
│   ├── player/           # Media3 播放服务与播放器管理
│   └── ui/               # 通用 UI 组件与主题
├── feature/
│   ├── login/            # 登录相关页面与 ViewModel
│   ├── home/             # 首页推荐内容
│   ├── search/           # 搜索与搜索结果
│   ├── AlbumList/        # 专辑详情
│   ├── PlayList/         # 歌单详情
│   ├── artist/           # 歌手详情
│   └── player/           # 播放器详情页
├── build-logic/          # 自定义 Gradle convention plugins
├── gradle/               # Gradle wrapper 与版本目录
└── settings.gradle.kts   # 模块声明
```

## 模块说明

| 模块 | 说明 |
| --- | --- |
| `:app` | 应用主模块，负责 Application、MainActivity、Compose 导航、迷你播放器和页面组装 |
| `:core:common` | 通用工具代码 |
| `:core:ui` | 主题、搜索框、加载占位等通用 UI 组件 |
| `:core:data` | 网络请求、数据模型、Repository、DataStore、接口依赖注入 |
| `:core:player` | 音乐播放服务、播放管理器和 Media3 集成 |
| `:feature:home` | 首页数据展示 |
| `:feature:search` | 搜索页与搜索结果详情 |
| `:feature:AlbumList` | 专辑详情页 |
| `:feature:PlayList` | 歌单详情页 |
| `:feature:artist` | 歌手详情页 |
| `:feature:player` | 播放器主界面、歌词、进度控制 |
| `:feature:login` | 登录相关功能 |

## 环境要求

- Android Studio：建议使用较新的稳定版
- JDK：11 或以上
- Gradle：使用项目内置 Gradle Wrapper
- Android SDK：compileSdk 36，minSdk 26
- 网络环境：项目需要访问远程音乐接口

## 快速开始

1. 克隆或打开项目：

```bash
git clone <项目地址>
cd MusicApp
```

2. 使用 Android Studio 打开项目根目录。

3. 等待 Gradle Sync 完成。

4. 连接 Android 设备或启动模拟器。

5. 运行 `app` 模块。

也可以使用命令行构建 Debug 包：

```bash
./gradlew assembleDebug
```

Windows 环境可使用：

```powershell
.\gradlew.bat assembleDebug
```

## 常用命令

```bash
# 构建 Debug APK
./gradlew assembleDebug

# 运行单元测试
./gradlew test

# 运行 Android Lint
./gradlew lint

# 构建 Release APK
./gradlew assembleRelease
```

## 网络接口说明

项目当前在 `core:data` 模块中配置 Retrofit，基础地址为：

```text
https://api-enhanced-henna.vercel.app/
```

网络层会通过 OkHttp 拦截器自动处理两件事：

- 从 `UserSessionManager` 读取 Cookie 并添加到请求头
- 为请求追加 `randomCNIP=true` 查询参数

如果接口地址发生变化，可以在以下文件中修改：

```text
core/data/src/main/java/com/example/data/di/NetworkModule.kt
```

## 权限说明

项目在 AndroidManifest 中声明了以下权限：

- `INTERNET`：访问远程音乐接口
- `FOREGROUND_SERVICE`：运行前台服务
- `FOREGROUND_SERVICE_MEDIA_PLAYBACK`：媒体播放前台服务
- `POST_NOTIFICATIONS`：Android 13 及以上通知权限
- `READ_EXTERNAL_STORAGE`：读取本地存储资源

## 运行流程概览

1. `MainActivity` 启动后申请通知权限，并加载 `MusicAppTheme`。
2. `MainScreen` 创建 Compose Navigation，并展示全局 `MiniPlayer`。
3. 首页通过 `HomeViewModel` 请求 Banner、推荐歌单、新专辑、排行榜和热门歌手数据。
4. 用户进入歌单、专辑、歌手或搜索详情页后，各 feature 模块通过对应 Repository 获取数据。
5. 播放相关操作交由 `core:player` 中的播放器管理与 Media3 服务处理。
6. 播放页展示歌曲封面、歌词、进度条和播放控制。

