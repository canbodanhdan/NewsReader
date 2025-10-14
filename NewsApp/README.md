# NewsApp - Android News Reader

A modern Android news reader application with Vietnamese language support and beautiful UI.

## 🚀 Features

- **📰 News Reading**: Browse latest news from multiple sources
- **🔄 Refresh Button**: Quick refresh with auto-scroll to top
- **⭐ Feedback System**: Beautiful feedback dialog with 5-star rating
- **🇻🇳 Vietnamese Support**: Full Vietnamese language support with diacritics
- **💾 Local Storage**: Uses SharedPreferences for data persistence
- **🔖 Bookmarks**: Save and manage favorite articles
- **📚 History**: Track reading history
- **👤 User Management**: Login/Register system

## 🛠️ Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 26+
- Java 11+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/canbodanhdan/NewsReader.git
   cd NewsReader
   ```

2. **Open in Android Studio**
   - File → Open → Select NewsReader folder
   - Wait for Gradle sync to complete

3. **Configure API Key**
   - Open `app/src/main/java/vn/edu/usth/newsreader/config/ApiConfig.java`
   - Replace `YOUR_API_KEY_HERE` with your NewsAPI key
   - Get free API key at: https://newsapi.org/

4. **Build and Run**
   - Build → Make Project (Ctrl+F9)
   - Run → Run 'app' (Shift+F10)

## 📱 Screenshots

- Modern UI with dark theme
- Refresh button in header
- Vietnamese feedback dialog
- Smooth animations and transitions

## 🏗️ Architecture

- **MVVM Pattern**: Clean architecture with ViewModels
- **SharedPreferences**: Lightweight local storage
- **Retrofit**: Network communication
- **Material Design**: Modern UI components
- **Navigation Component**: Fragment navigation

## 🔧 Technical Details

- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)
- **Language**: Java
- **Build Tool**: Gradle
- **Dependencies**: Retrofit, Glide, Material Components

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

For support, email feedback@newsapp.com or create an issue in this repository.

---

Made with ❤️ for Vietnamese users
