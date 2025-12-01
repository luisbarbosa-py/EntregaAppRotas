# Aplicativo de Rotas

## Descrição do App

Este aplicativo tem como objetivo gerenciar as rotas de transporte escolar, permitindo que o motorista organize e controle os alunos que utilizam o serviço. O app facilita a manutenção de um cadastro de alunos, condutores, escolas e responsáveis.

## Telas Principais

### Tela de login
<img width="265" height="462" alt="tela-principal" src="https://github.com/user-attachments/assets/4b2a48f3-4ecb-4404-8be0-259d18a555ab" />


### Tela Home
<img width="336" height="703" alt="home" src="https://github.com/user-attachments/assets/4c88e42c-e409-4e2a-aea7-ef32257f8f5a" />



### Lista de Turmas
<img width="265" height="439" alt="lista-turmas" src="https://github.com/user-attachments/assets/f0af5e7f-872d-4e88-a6b7-ba3e02a69d49" />


### Alunos da Turma
<img width="264" height="439" alt="alunos-turma" src="https://github.com/user-attachments/assets/a9183cfb-44fb-4f0e-b5b2-22523a68d8a0" />


### Lista de Alunos
<img width="253" height="371" alt="lista-alunos" src="https://github.com/user-attachments/assets/8b94d03e-ba14-4257-aa0e-d7ba038d1927" />


### Lista de Condutores
<img width="265" height="444" alt="lista-condutores" src="https://github.com/user-attachments/assets/d01ff15a-48e9-4eb0-8187-f7c1444d8182" />


### Lista de Escolas
<img width="257" height="371" alt="lista-escolas" src="https://github.com/user-attachments/assets/0b1e046e-4574-44a9-b769-656058a2d22d" />


### Lista de Responsáveis
<img width="261" height="437" alt="lista-responsaveis" src="https://github.com/user-attachments/assets/c2e8ec7d-edde-4f9b-8db7-9b2c4a96679b" />


### Cadastro de Turma
<img width="263" height="385" alt="cadastro-turma" src="https://github.com/user-attachments/assets/b777bf27-193b-49e5-b801-c15bdd48f482" />


### Cadastro de Condutor
<img width="260" height="384" alt="cadastro-condutor" src="https://github.com/user-attachments/assets/060840eb-f4fa-424d-b8f7-da93ff71dfaf" />


### Cadastro de Escola
<img width="261" height="419" alt="cadastro-escola" src="https://github.com/user-attachments/assets/f7fc54cf-2efb-4c58-aa2d-c4980151e08e" />


### Cadastro de Responsável
<img width="263" height="467" alt="cadastro-responsaveis" src="https://github.com/user-attachments/assets/5f84618c-ba46-47a1-8141-608d557565aa" />


## Tecnologias Utilizadas

*   **Kotlin:** Linguagem de programação principal.
*   **Android Jetpack:**
    *   **Room:** Para persistência de dados local (banco de dados).
    *   **Lifecycle (ViewModel e LiveData):** Para gerenciar o ciclo de vida dos componentes da interface e os dados.
    *   **Navigation Component:** Para gerenciar a navegação entre as telas do app.
    *   **ViewBinding:** Para acessar as views do layout de forma segura.
*   **Material Design:** Para os componentes de interface do usuário.
*   **CircleImageView:** Para exibir imagens em formato circular.
*   **Integração com API ViaCEP:**  
    Implementada utilizando Retrofit para realizar consultas de endereço a partir do CEP, retornando os dados automaticamente através da API pública ViaCEP.

Funcionamento

A integração utiliza requisições GET para consumir a API pública do ViaCEP, sem necessidade de chaves de autenticação. Basta informar o CEP para que o serviço retorne os dados do endereço em formato JSON.

## Como Instalar e Rodar

1.  Clone este repositório.
2.  Abra o projeto no Android Studio.
3.  Compile e execute o aplicativo em um emulador ou dispositivo Android.

Não há necessidade de configurações adicionais, como chaves de API.

## Funcionamento do CRUD

O aplicativo implementa as funcionalidades de Criar, Ler, Atualizar e Deletar (CRUD) para as seguintes entidades:

*   **Alunos**
*   **Condutores**
*   **Escolas**
*   **Responsáveis**

Todas as informações são armazenadas localmente em um banco de dados Room. As telas do aplicativo permitem que o usuário adicione, edite, visualize e remova registros de cada uma dessas entidades.

## Autores

*   **Ronaldo** - RA: 2403661
*   **Luis** - RA: 2402947
*   **Maycon** - RA: 2402929

