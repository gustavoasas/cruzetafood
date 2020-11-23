# cruzetafood


## Windows 

JDK utilizado para o projeto foi o OpenJDK 14:
https://jdk.java.net/archive/

Pode ser baixado aqui:
https://download.java.net/java/GA/jdk14.0.2/205943a0976c4ed48cb16f1043c5c647/12/GPL/openjdk-14.0.2_windows-x64_bin.zip

Para esse projeto será necessário a configuração do seu Build de Desenvolvimento a partir do configurador do lombok (lombok.jar) que você pode encontrar aqui: https://projectlombok.org/download


## Linux

Baixe a versão do OpenJDK 14 no [repositório oficial](http://jdk.java.net/archive/ "Pagina oficial para download do OpenJDK") 

Após baixar crie o diretório /opt/java

```console
$ sudo mkdir /opt/java
```

Mova o arquivo baixado para a pasta criada
```console
$ sudo mv /home/user/Download/openjdk-14.0.2_linux-x64_bin.tar.gz /opt/java/
```

Após copiar descompacte o arquivo
```console
$ sudo tar -zxvf openjdk-14.0.2_linux-x64_bin.tar.gz
```

Instale a nova versão do javac
```console
$ sudo update-alternatives --install /usr/bin/javac javac /opt/java/jdk-14.0.2/
```

Instale a nova versão do java
```console
$ sudo update-alternatives --install /usr/bin/javac javac /opt/java/jdk-14.0.2/bin/javac 1
```

Configurando a nova versão do javac instalada
```console
$ sudo update-alternatives --config javac
```
Escolha a opção correspondente a /opt/java/jdk-14.0.2/bin/javac e pressione Enter


Configurando a nova versão do java instalada
```console
$ sudo update-alternatives --config java
```
Escolha a opção correspondente a /opt/java/jdk-14.0.2/bin/java e pressione Enter


Confira a versão do java instalada
```console
$ java --version
```
