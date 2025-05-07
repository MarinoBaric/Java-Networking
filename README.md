# Java Networking

<div align="center">

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Networking](https://img.shields.io/badge/Networking-007396?style=for-the-badge&logo=socket.io&logoColor=white)
![License](https://img.shields.io/github/license/MarinoBaric/Java-Networking?style=for-the-badge)
![Last Commit](https://img.shields.io/github/last-commit/MarinoBaric/Java-Networking?style=for-the-badge)

</div>

<p align="center">
  <img src="https://user-images.githubusercontent.com/74038190/212749447-bfb7e725-6987-49d9-ae85-2015e3e7cc41.gif" width="500">
</p>

## 📝 Overview

This repository contains practice implementations for Java networking concepts, focusing on client-server architecture. It demonstrates the fundamentals of socket programming, network protocols, and distributed application development using Java's built-in networking capabilities.

## ✨ Features

- **TCP/IP Socket Implementation**: Reliable connection-oriented communication
- **Client-Server Architecture**: Complete implementation of both client and server components
- **Multithreaded Server**: Handle multiple client connections simultaneously
- **Protocol Design**: Custom application-level protocols for client-server communication
- **Error Handling**: Robust error handling for network exceptions and edge cases

## 🛠️ Technologies Used

- Java SE
- Java IO Streams
- Java Network API (java.net package)
- Socket Programming
- Multithreading

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Basic understanding of networking concepts
- Any IDE that supports Java (IntelliJ IDEA, Eclipse, etc.)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/MarinoBaric/Java-Networking.git
```

2. Navigate to the project directory:
```bash
cd Java-Networking
```

3. Compile the Java files:
```bash
javac -d bin src/*.java
```

## 💻 Usage

### Running the Server

```bash
java -cp bin Server [port]
```

### Running the Client

```bash
java -cp bin Client [server_address] [port]
```

## 📂 Project Structure

```
Java-Networking/
├── src/
│   ├── Server.java        # Server implementation
│   ├── Client.java        # Client implementation
│   ├── Protocol.java      # Communication protocol definition
│   └── utils/             # Utility classes
├── examples/              # Example implementations
│   ├── ChatApplication/   # Simple chat application example
│   └── FileTransfer/      # File transfer example
├── docs/                  # Documentation
└── README.md              # Project documentation
```

## 📊 Network Flow Diagram

<p align="center">
  <img src="https://mermaid.ink/img/pako:eNptkc9qwzAMh1_F-JRCYHuAPYbRboc1dMnFcpSa1LGx5I2S0tL03TenheKx-iD0-_RJ1ggfNkAGFTg_u-BRXeGXtKRVlUPJjW9JQfW4B8ooqG6qGqpL07SfUlY918qzf4NuazXL3k3AvWOcwx73hOsLwUUjUqbSWa4ckJUphHBc7KjY-rPsxLQYPHWzD10Dx5GdVPa7wTCz8_Gxc9a7nSoVr2vSPR0M_sfmRNxwn-_GrJPJpDLUxXqeC8x1wovpO0eFnLfcRmJFznnNpgkj8WZjp-i7P7_Hqx_r1WMG0VLPGTgxdMY1LTTkyGo8pyqEV3I25GZgRwrZ29hwgUyGIyXYTDGDEXsOFFYZ_AFZ-YCO" alt="Network Flow">
</p>

## 🧪 Examples

### Basic Client-Server Communication

Server:
```java
ServerSocket serverSocket = new ServerSocket(8080);
System.out.println("Server started on port 8080");
Socket clientSocket = serverSocket.accept();
System.out.println("Client connected: " + clientSocket.getInetAddress());

PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

String inputLine;
while ((inputLine = in.readLine()) != null) {
    System.out.println("Received: " + inputLine);
    out.println("Echo: " + inputLine);
}
```

Client:
```java
Socket socket = new Socket("localhost", 8080);
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

String userInput;
while ((userInput = stdIn.readLine()) != null) {
    out.println(userInput);
    System.out.println("Server response: " + in.readLine());
}
```

## 📚 Learning Resources

- [Oracle Java Networking Tutorial](https://docs.oracle.com/javase/tutorial/networking/)
- [Java Network Programming, 4th Edition (O'Reilly)](https://www.oreilly.com/library/view/java-network-programming/9781449365936/)
- [Socket Programming in Java (Baeldung)](https://www.baeldung.com/a-guide-to-java-sockets)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Marino Barić**

* GitHub: [@MarinoBaric](https://github.com/MarinoBaric)

---

<div align="center">
  <sub>Built with ❤️ by Marino Barić</sub>
</div>
