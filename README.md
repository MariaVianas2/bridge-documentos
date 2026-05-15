# Sistema de Geração de Documentos com Padrão Bridge

## 📌 Descrição do Projeto

Este projeto foi desenvolvido em Java com o objetivo de aplicar o padrão estrutural Bridge, permitindo a separação entre os tipos de documentos empresariais e os formatos de saída dos arquivos.

A aplicação possibilita gerar diferentes documentos em múltiplos formatos sem modificar o código existente, promovendo desacoplamento e extensibilidade.
---

## LINK DO VIDEO

https://youtu.be/TtVAC9rizG4

---

# 🎯 Objetivo

Permitir que:

- Um mesmo documento seja exportado em diferentes formatos;
- Novos documentos possam ser adicionados facilmente;
- Novos formatos de saída possam ser criados sem alterar os documentos existentes.

---

# 🏗️ Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

```txt
src/
├── abstraction
├── implementation
├── concrete
├── factory
├── app
└── web
```

---

# 🔗 Aplicação do Padrão Bridge

## Abstração

A classe abstrata `Documento` representa os documentos empresariais.

```java
public abstract class Documento {
    protected GeradorArquivo gerador;

    public Documento(GeradorArquivo gerador) {
        this.gerador = gerador;
    }

    public abstract void gerar();
}
```

## Implementação

A interface `GeradorArquivo` representa os formatos de saída.

```java
public interface GeradorArquivo {
    void escrever(String conteudo);
}
```

Com isso, qualquer documento pode utilizar qualquer formato de saída.

---

# 📚 Documentos Implementados

- Relatório Gerencial
- Nota Fiscal
- Proposta Comercial
- Contrato Simplificado

Cada documento possui estrutura própria e informações específicas.

---

# 💾 Formatos de Saída

- TXT
- HTML
- PDF (simulado via console)
- JSON
- XML (bônus)

---

# 🔀 Combinações Demonstradas

- Relatório Gerencial em PDF
- Proposta Comercial em HTML
- Nota Fiscal em JSON
- Contrato Simplificado em TXT

---

# ⭐ Desafios Extras Implementados

✅ Exportação para XML  
✅ Inclusão de múltiplos itens na nota fiscal  
✅ Sistema configurável via input  
✅ Uso do padrão Factory em conjunto com Bridge  
✅ Interface web simples em HTML e CSS

---

# ▶️ Como Executar

## Compilar o projeto

### PowerShell

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
```

---

## Executar a aplicação principal

```powershell
java -cp out app.Main
```

---

## Executar o sistema configurável

```powershell
java -cp out app.AppConfiguravel
```

---

## Executar a interface web

```powershell
java -cp out web.ServidorBridge
```

Depois acesse:

```txt
http://localhost:8080
```

---

# 📂 Saída dos Arquivos

Os arquivos gerados são salvos automaticamente na pasta:

```txt
saida/
```

---

# 🧠 Tecnologias Utilizadas

- Java
- HTML
- CSS
- Padrão Bridge
- Padrão Factory

---

# 👩‍💻 Desenvolvido por

Maria Eduarda Melo Viana
