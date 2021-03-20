# ScalaGram - Report

- [ScalaGram - Report](#scalagram---report)
  - [1. Development process [Rossi]](#1-development-process-rossi)
    - [Divisione dei task](#divisione-dei-task)
    - [Meeting ed interazioni](#meeting-ed-interazioni)
    - [Strumenti utilizzati](#strumenti-utilizzati)
  - [2. Requirements [Gruppo]](#2-requirements-gruppo)
    - [Business](#business)
      - [Ubiquitous language](#ubiquitous-language)
      - [Knowledge crunching](#knowledge-crunching)
    - [Utente](#utente)
    - [Funzionali](#funzionali)
      - [User stories](#user-stories)
      - [DSL](#dsl)
    - [Non funzionali](#non-funzionali)
    - [Implementazione](#implementazione)
  - [3. Architectural Design [Gruppo - Pistocchi]](#3-architectural-design-gruppo---pistocchi)
    - [Bounded context](#bounded-context)
    - [DSL e user story [Pistocchi]](#dsl-e-user-story-pistocchi)
  - [4. Design Detail](#4-design-detail)
    - [Scelte rilevanti [Boschi]](#scelte-rilevanti-boschi)
    - [Organizzazione del codice [Rossi, Tumedei]](#organizzazione-del-codice-rossi-tumedei)
  - [5. Implementation](#5-implementation)
    - [Implementazione - Gianni Tumedei [Logica bot]](#implementazione---gianni-tumedei-logica-bot)
    - [Implementazione - Francesco Boschi [Modelli, marshalling]](#implementazione---francesco-boschi-modelli-marshalling)
      - [Package PPS19.scalagram.models](#package-pps19scalagrammodels)
      - [Package PPS19.scalagram.marshalling](#package-pps19scalagrammarshalling)
    - [Implementazione - Mattia Rossi [Metodi]](#implementazione---mattia-rossi-metodi)
    - [Attività di gruppo [Gruppo]](#attività-di-gruppo-gruppo)
      - [DSL](#dsl-1)
  - [6. OPS](#6-ops)
    - [Automatic delivery e deployment [Rossi, Pistocchi]](#automatic-delivery-e-deployment-rossi-pistocchi)
    - [Build automation [Rossi, Pistocchi]](#build-automation-rossi-pistocchi)
    - [Licensing [Rossi]](#licensing-rossi)
    - [QA [Boschi, Tumedei]](#qa-boschi-tumedei)
      - [Testing](#testing)
        - [Testing automatizzato](#testing-automatizzato)
        - [Testing non automatizzato](#testing-non-automatizzato)
        - [Copertura dei test](#copertura-dei-test)
  - [7. Retrospective [Rossi, Optional[Tumedei]]](#7-retrospective-rossi-optionaltumedei)
    - [Sprint 1](#sprint-1)
    - [Sprint 2](#sprint-2)
    - [Sprint 3](#sprint-3)
    - [Sprint 4](#sprint-4)
    - [Sprint 5](#sprint-5)
    - [Sprint 6](#sprint-6)
    - [Sprint 7](#sprint-7)
    - [Sprint 8](#sprint-8)
  - [8. Conclusioni [Gruppo]](#8-conclusioni-gruppo)
    - [Sviluppi futuri](#sviluppi-futuri)
    - [Conclusioni](#conclusioni)

## 1. Development process [Rossi]
Lo sviluppo del sistema verrà effettuato adottando un processo simil-Scrum, viste le ridotte dimensioni del team e la conseguente impossibilità di adottare Scrum in pieno. L'approccio utilizzato prevede la suddivisione in Scrum-Task anche di tutta la parte progettuale del sistema e di bootstrap del progetto, comprese la definizione dei requisiti, la configurazione degli ambienti (IntelliJ, Gradle, Github e Github Actions) e la stesura di questo report.

### Divisione dei task
La divisione dei ruoli all'interno del team è la seguente:

**Gianni Tumedei**: *product owner* e *sviluppatore*. Responsabile dello sviluppo e testing delle seguenti funzionalità:
- Gestione della logica di funzionamento del bot
- Gestione delle modalità di recupero degli update

**Francesco Boschi**: *scrum master* e *sviluppatore*. Responsabile dello sviluppo e testing delle seguenti funzionalità:
- Gestione dei modelli delle entità del sistema
- Codifica e decodifica delle informazioni

**Mattia Rossi**: *sviluppatore*. Responsabile dello sviluppo e testing delle seguenti funzionalità:
- Gestione dei metodi di Telegram
- Gestione degli errori e dell'output

Per quanto concerne la realizzazione delle componenti sviluppate in comune, queste sono le seguenti:
- Sviluppo del DSL
- Creazione dei Bot di esempio

### Meeting ed interazioni
I componenti del team si prefissano di realizzare meeting con cadenza giornaliera tramite videochiamate, principalmente effettuate tramite Microsoft Teams e Discord, al fine di mantenersi aggiornati sullo stato del progetto e sull'avanzamento dei singoli task. Con cadenza settimanale, per la precisione alla fine di ogni sprint, si terrà invece un meeting in cui verranno definiti i task da includere nella fase di sprint successiva. Le interazioni tra i componenti del team si mantengono comunque frequenti per convenire su eventuali dettagli di minore entità all'interno del progetto.
### Strumenti utilizzati
- **IntelliJ IDEA**: IDE utilizzato per lo sviluppo del progetto, scelto perché fornisce supporto completo per lavorare con il linguaggio Scala
- **Git**: utilizzato come version control system per tenere traccia dello sviluppo in itinere del progetto. Sono stati tracciati i file sorgente, di test, di configurazione della build e quelli di backlog.
- **Github**: scelto come servizio di repository
- Gradle: utilizzato come tool per eseguire la build del sistema
- **Github Actions**: servizio integrato all'interno di Github per la CI (continuos integration) per eseguire la build del progetto in sistemi eterogenei
- **Scoverage**: utilizzato per calcolare la copertura dei test implementati, al fine di stabilire la percentuale minima di sistema non "coperto" e quindi soggetto a eventuali bug.
- **Trello**: è stato utilizzato in maniera distribuita per definire nel dettaglio le attività presenti nel backlog. In particolare sono state definite le seguenti sezioni:
  - To do: task che devono ancora essere sviluppati
  - In progress: lo sviluppo del task è iniziato ma non concluso
  - Done: contiene i task che sono stati completati
  - Paused: contiene i task che erano in progresso e sono stati momentaneamente sospesi
  - Aborted: contiene i task definitivamente cancellati
- **Google Docs**: utilizzato sia per scrivere il backlog settimanale che gli appunti riguardanti il design,
-  l'architettura e le user story del progetto
## 2. Requirements [Gruppo]
Requisiti (delle varie tipologie, ossia: 1) business, 2) utente, 3) funzionali, 4) non funzionali, 5) di implementazione)

### Business

Questa sezione è dedicata all'analisi e definizione dei requisiti di business che caratterizzeranno il sistema. L'approccio utilizzato per la definizione del modello è basato sulla filosofia **Domain Driven Design** (DDD).\
L'obiettivo del progetto è lo sviluppo di una libreria per la creazione di bot per la piattaforma di messaggistica Telegram.\
Il progetto è nato grazie alla passione di alcuni membri del gruppo per lo sviluppo di bot Telegram e a causa dell'assenza di un pratico framework/libreria in Scala che li soddisfacesse.
Questo capito è suddiviso nelle sezioni Ubiquitous language e Knowledge crunching.

#### Ubiquitous language
Questa sezione riporta le terminologie tecniche che sono emerse durante durante tutto lo svolgimento del progetto accompagnate dalla loro definizione.\
Nei capitoli successivi di questo documento si farà riferimento a questi termini dandone per assodato il significato.
| Termine                        | Significato                                                                                                                                                                                                                                                                                                                                                     |
| ------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| User/Utente                    | Un entità che interagisce con Telegram. Può essere sia un utente umano che un programma (Bot). È identificato da uno user ID, che è anche usato da altri utenti per interagire con esso.                                                                                                                                                                        |
| Human user/Utente umano        | Persona identificata dall'account che utilizza.                                                                                                                                                                                                                                                                                                                |
| Account                        | Un account di Telegram identifica un utente umano ed è associato con un numero telefonico/email univoco e un User.                                                                                                                                                                                                                                              |
| Bot                            | User automatizzato. Un Bot deve essere creato da un utente umano, che ne è anche il proprietario. Un Bot è identificato da un token univoco.                                                                                                                                                                                                                    |
| Chat                           | Una conversazione tra due o più utenti, identificata da un ID.                                                                                                                                                                                                                                                                                                  |
| Private chat/Chat privata      | una conversazione tra due utenti.                                                                                                                                                                                                                                                                                                                               |
| Channel/Canale                 | Una chat a senso unico, in cui gli amministratori possono pubblicare messaggi (in questo caso chiamati anche post), e gli utenti (in questo caso chiamati anche iscritti o subscribers) possono leggerli. I canali sono identificati da un chat ID e da un channel name.                                                                                        |
| Gruppo                         | Una conversazione tra un gruppo di utenti. Il gruppo è gestito da amministratori.                                                                                                                                                                                                                                                                               |
| Private channel/Canale privato | Canale a cui hanno accesso solamente amministratori e utenti iscritti, che possono essere aggiunti manualmente dagli amministratori oppure iscriversi tramite un link di invito.                                                                                                                                                                                |
| Public channel/Canale pubblico | Canale di cui chiunque può visualizzare i messaggi (anche senza effettuare il login su Telegram) e chiunque può iscriversi.                                                                                                                                                                                                                                     |
| Message/Messaggio              | Qualsiasi contenuto inviato in una chat o in un canale. I più semplici sono messaggi di testo, ma altri formati includono note vocali e video, immagini statiche, GIF, file audio, file video, qualsiasi altro file, adesivo, sondaggio, posizione, contatto. I Bot possono anche inviare messaggi di risposta. Un messaggio è identificato da un ID messaggio. |
| Command/Comando                | Un messaggio che inizia con il carattere "/", utile per invocare in maniera semplice dei trigger.                                                                                                                                                                                                                                                               |
| Pin                            | Azione di fissare un messaggio in una chat, per mantenerli sempre visibili nella parte alta.                                                                                                                                                                                                                                                                    |
| Unpin                          | Rimozione del pin da un messaggio fissato. Può essere eseguita sul singolo o su tutti i messaggi fissati.                                                                                                                                                                                                                                                       |
| Update                         | Tutti gli eventi che accadono all'interno di Telegram sono wrappati in un update, che viene inviato agli utenti interessati ed è identificato da un update ID.                                                                                                                                                                                                  |
| Polling                        | Modalità di reperimento degli update nella quale si contattano periodicamente i server di Telegram per scaricare quelli disponibili.                                                                                                                                                                                                                            |
| Webhook                        | Modalità di reperimento degli update nella quale sono sono i server di Telegram a inoltrarli ad un URL pubblico. Richiede una fase di configurazione nella quale il token del bot viene associato a tale URL.                                                                                                                                                   |
| Token                          | Identificatore univoco di un bot. Tramite esso è possibile controllare le azioni del bot, pertanto va mantenuto privato. Può essere trasferito da un utente ad un altro per cambiarne il proprietario.                                                                                                                                                          |
| Trigger                        | Logica secondo la quale un bot reagisce ad un update.                                                                                                                                                                                                                                                                                                           |
| Action/Azione                  | Una serie di attività eseguite in seguito all'attivazione di un trigger.                                                                                                                                                                                                                                                                                        |
| Reaction                       | Combinazione di un trigger e dell'azione corrispondente.                                                                                                                                                                                                                                                                                                        |
| Context/Contesto               | Container per tutti i dati che un bot deve memorizzare tra un update e l'altro. Il context è identificato dalla chat a cui fa riferimento e contiene soltanto i dati ad essa relativi in modo da isolare le varie conversazioni.                                                                                                                                |
| Scene/Scena                    | Interazione ben definita con il bot composta da uno o più step che devono essere eseguiti in un certo ordine.                                                                                                                                                                                                                                                   |
| Step                           | Reaction all'interno di una scena.                                                                                                                                                                                                                                                                                                                              |
| Middleware                     | Elemento che processa ogni update e determina se inoltrarlo e scartarlo.                                                                                                                                                                                                                                                                                        |

#### Knowledge crunching
Questa è stata la fase iniziale del progetto, durante la quale si è cercato di effettuare una panoramica sul dominio che consiste nella piattaforma Telegram e nelle relative [Bot API](https://core.telegram.org/bots/api).\
La maggior parte delle terminologie inserite nella sezione Ubiquitous Language sono emerse durante questa fase e non sono quindi riportati anche in questo capitolo.

Telegram è un è un servizio di messaggistica istantanea e broadcasting basato su cloud. I client ufficiali di Telegram sno distribuiti sotto forma di software open-source per Android, Linux, IOS, MacOS e Microsoft Windows. \
Dal 2015 Telegram ha introdotto due funzionalità che lo hanno distinto particolarmente dalla concorrenza: i canali e una piattaforma dedicata alla creazione e alla gestione di bot.\
Questi ultimi sono utenti virtuali che forniscono molteplici funzionalità in maniera totalmente automatizzata e sono l'oggetto di principale interesse del progetto.

La creazione stessa dei bot e la loro gestione sono demandate al bot ufficiale di Telegram, BotFather, che permette ad ogni utente di:

- Creare, elencare e cancellare i propri bot
- Modificare nome, descrizione, bio, immagine, comandi suggeriti e impostazioni di privacy e pagamenti dei propri bot
- Trasferire la proprietà dei bot ad altri utenti

BotFather non consente quindi di definire il comportamento dei bot. Tale compito deve essere definito in un software esterno che ottiene aggiornamenti da Telegram in modalità push (webhook mode) o pull (polling mode) e li processa secondo la logica definita dal programmatore del bot.

Telegram mette a disposizione delle [API](https://core.telegram.org/bots/api) per permettere lo sviluppo dei bot.\
La community Telegram ha creato numerose librerie in vari linguaggi di programmazione che forniscono astrazioni di più alto livello sulle API. Il progetto Scalagram ricade in questa categoria.

### Utente
Gli utenti finali del progetto sono gli sviluppatori che sfruttano la libreria Scalagram per la realizzazione di bot Telegram.\
La libreria deve mettere a disposizione gli strumenti per la programmazione di un bot sfruttando un apposito DSL.\
Nel caso in cui un utilizzatore abbia necessità specifiche, deve inoltre essere possibile utilizzare qualsiasi funzionalità della libreria senza far uso del DSL.
### Funzionali

Le funzionalità che la libreria deve mettere a disposizione sono state definite tramite la stesura delle user stories e del diagramma dei casi d'uso. 

#### User stories

Al termine della fase di knowledge crunching si sono sviluppate user stories col fine di poter definire dettagliatamente i principali casi d'uso della libreria da parte di un utente finale, ossia uno sviluppatore software. Questo passaggio è risultato cruciale per poter in seguito definire un DSL che ricalcasse le user stories con una nuova sintassi dichiarativa, sviluppata ad hoc per venire in contro alle esigente del cliente.

Le user stories sono definite dal punto di vista di un developer, che deve poter: 

- Definire il token appartenente al bot
- Definire un middleware associato al bot
- Definire una specifica reaction eseguibile dal bot composta da trigger e action
- Definire uno step per una specifica scena
- Avviare l'esecuzione del bot in modalità polling, opzionalmente specificandone i parametri
- Bypassare il DSL in caso di esigenze particolari

A partire dalle user stories è stato definito il seguente diagramma dei casi d'uso:

<p align="center">
  <img src="./img/use-case.png" alt="Use case" height="750"/>
</p>

#### DSL

Comparare la user story con i pezzi di codice/pseudocodice del DSL.

### Non funzionali

Dal momento che implementare tutti i metodi resi disponibili dalle API di Telegram e la modalità webhook avrebbe richiesto un tempo superiore a quello disponibile per il progetto, alcune funzionalità non sono state implementate. Per questo motivo il team si è posto come obiettivo quello di realizzare la libreria adottando un'architettura facilmente estendibile, in modo da far fronte a eventuali sviluppi futuri. 

### Implementazione

## 3. Architectural Design [Gruppo - Pistocchi]
Design architetturale (architettura complessiva, descrizione di pattern architetturali usati, componenti del sistema distribuito, scelte tecnologiche cruciali ai fini architetturali -- corredato da pochi ma efficaci diagrammi)

### Bounded context

Lo studio del problema ha portato a definire tre aree critiche per la definizione del sistema, le quali necessitano un importante isolamento, al fine di garantire indipendenza e chiara suddivisione dei moduli durante la fase di sviluppo. Una corretta suddivisione dei bounded context in fase iniziale permetterà di scomporre in maniera più chiara il lavoro.

Sono stati definiti i seguenti bounded context:
- **Bot logic context**: è il core del sistema, comprende le funzionalità dedicate alla definizione della logica di comportamento del bot
- **Telegram API calls context**: racchiude tutte le interazioni con le API di Telegram. Ad esempio, il download degli update e l'invio dei messaggi sono isolati in questo bounded context
- **Update retrieval context**: si appoggia al context delle Telegram API calls per ottenere gli update e si occupa quindi del loro smistamento

Di seguito è riportata la context map del progetto, da notare che i modelli relativi alle entità restituite dalle API di Telegram sono legati più strettamente al context delle Telegram API calls, ma vengono sfruttati di frequente anche dalla logica del bot, pertanto si trovano in un'intersezione tra i due context. 

Queste decisioni impatteranno in maniera significativa successivamente, quando sarà necessario organizzare e scomporre i moduli di basso livello.

<p align="center">
  <img src="./img/context-map.png" alt="Context Map" height="350"/>
</p>

### DSL e user story [Pistocchi]
Architettura del dsl?

## 4. Design Detail
Design di dettaglio (scelte rilevanti, pattern di progettazione, organizzazione del codice -- corredato da pochi ma efficaci diagrammi)

### Scelte rilevanti [Boschi]
In fase di design, si è deciso di seguire la suddivisione definita tramite i Bounded Context, identificando così tre macro aree sviluppabili in maniera indipendente e di conseguenza parallelizzabili le quali, una volta terminate, sarebbero poi state integrate.

Nello sviluppo del DSL, col fine di avere un linguaggio il più possibile comprensibile e intuitivo, si è fatto ampio uso dello **zucchero sintattico** messo a disposizione da Scala, come per esempio:

- possibilità di utilizzare metodi unari come operatori **infissi**;
- pattern **Pimp my library** per fornire in maniera implicita metodi, conversioni ed estendere tipi esistenti;
- possibilità di utilizzare **parentesi graffe** per istanziare liste con un solo argomento;
- possibilità di omettere la parola chiave **new** nella creazione di un istanza.

L'implementazione dei modelli atti a rappresentare le entità fondamentali è stata definita **adattandosi** alle [Telegram Bot API](https://core.telegram.org/bots/api), identificando all'interno di classi create ad hoc tutti i campi necessari a rappresentare gli elementi sfruttati da Telegram, richiamando quindi un paradigma OO-FP Mixed.\
Grazie a questa scelta, è stato possibile utilizzare la libreria [Circe](https://circe.github.io/circe/), atta a facilitare e rendere semiautomatiche le operazioni di codifica (in fase di invio) e decodifica (in fase di ricezione) dei json.

In maniera analoga ai modelli, anche la modalità di utilizzo delle **API** per interagire con il server Telegram è stata definita facendo riferimento alle direttive fornite dal servizio stesso.\
In questo caso, per garantire uno sviluppo più possibile funzionale, si è utilizzato il trio di classi [Try, Success, Failure](https://docs.scala-lang.org/overviews/scala-book/functional-error-handling.html), fondamentali per gestire gli errori in maniera **gracefully**, siano essi dovuti a problemi nella formattazione dell'URL, del body del messaggio o di connessione.\
Grazie a questa tecnica e all'utilizzo di classi di default nel caso in cui l'encoding/decoding dei json non andasse a buon fine, qualunque failure riesce ad essere intercettata senza causare interruzioni non volute del programma.

Per quanto concerne il testing, inizialmente si era optato per un **testing automatico** che, tramite le apposite chiamate HTTP al server Telegram, permettesse di verificare la correttezza sia nell'utilizzo delle API, che nell'encoding della richiesta e nel decoding della risposta.\
Poiché Telegram per evitare attacchi DOS prevede un limite massimo di richieste al minuto, è stato necessario optare per un approccio alternativo, in quanto l'esecuzione di più suite di test in contemporanea portava frequenti fallimenti nonostante le tecniche di retry adottate.\
La correttezza nell'utilizzo delle API viene quindi determinata solamente sulla base della composizione della richiesta stessa, ipotizzando che data una richiesta corretta, possa fallire solo per problemi legati a Telegram o alla connessione.\
Per la fase di interpretazione delle risposte, invece, si è deciso di memorizzare i json di interesse in appositi file e utilizzarli per verificare la correttezza delle operazioni di decodifica.
### Organizzazione del codice [Rossi, Tumedei]
L'organizzazione dei package del progetto riflette i bounded context definiti in fase di design. Il core delle funzionalità nei seguenti package:
- `methods`: corrisponde al bounded context **Telegram API calls** contiene l'implementazione di tutti i metodi delle API di Telegram che si è deciso di implementare nella libreria. I metodi principali in questo package sono `GetUpdates` e `SendMessage`.
- `modes`: corrisponde al bounded context **Update retrieval**, la modalità di download degli update che si è deciso di adottare è polling, la cui infrastruttura è implementata all'interno di questo package.
- `logic`: fa riferimento al bounded context **Bot logic**, questo package contiene tutti gli strumenti che la libreria fornisce agli sviluppatori per definire il comportamento del bot, ovvero come esso deve reagire a determinate situazioni. In particolare, il package `reactions` mette a disposizione una serie di factory per la creazione di reaction che permettono al bot di rispondere a eventi come la ricezione di messaggi, o l'entrata/uscita di un utente dalla chat. Questo package include anche l'implementazione delle seguenti classi core della libreria: `Scalagram`, `Context`, `Reaction`, `Scene` e `Middleware`.
- `models`: questo package è strettamente correlato con `methods` in quanto fornisce la definizione di tutte le entità inviate o ricevute tramite le API di Telegram, come `Update` e `TelegramMessage`.
- `dsl`: il dsl della libreria fa da wrapper alle sue funzionalità principali, tutte le definizioni necessarie per la sua realizzazione sono contenute in questo package. Esse sono suddivise in ulteriori package in base al concetto che rappresentano. I package in questione sono: `keyboard`, `middleware`, `mode`, `reaction` e `scene`.
- `marshalling`: contiene i metodi necessari per la conversione di stringhe tra camel case e snake case, utilizzati in fase di decodifica degli update e codifica delle richieste da inviare alle API di Telegram.
- `utils`: contiene alcuni metodi di utility privati utilizzati all'interno della libreria.
- `examples`: contiene alcuni bot di esempio a cui gli sviluppatori possono fare riferimento.

<p align="center">
  <img src="./img/code-organization.png" alt="code-organization" height="600"/>
</p>


## 5. Implementation
Implementazione (per ogni studente, una sotto-sezione descrittiva di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti non già presenti nel design)

### Implementazione - Gianni Tumedei [Logica bot]
### Implementazione - Francesco Boschi [Modelli, marshalling]
Boschi Francesco è responsabile dell'implementazione delle seguenti componenti:
#### Package PPS19.scalagram.models
Il seguente package, contiene tutti i file atti a definire le entità alla base del sistema e le operazioni di codifica e decodifica in json delle stesse.

Sebbene i modelli presenti siano in grande numero, la struttura utilizzata è simile per tutti e rispecchia il paradigma OO-FP Mixed, essendo presenti riferimenti al classico OO come gerarchie tra classi e trait atti a definire contratti comuni, oltre a elementi tipici di FP come companion object che fungono da contenitori di impliciti o Factory.

Elemento fondamentale che accomuna la maggior parte di queste classi, è la sezione dedicata alla **derivazione semiautomatica** messa a disposizione dalla libreria Circe.\
L'utilizzo di deriveDecoder, permette di decodificare in maniera automatica un json creando un oggetto della classe corrispondente, basandosi sul match tra i field del json e quelli della classe che verrà istanziata.\
Nel caso in cui un trait fosse ereditato da più classi, quindi, tramite un apposito implicito definito all'interno del **companion object**, viene selezionata la classe che sarà istanziata in maniera automatica o sulla base di parametri specifici, come nel caso della classe MessageEntity nella quale la derivazione viene fatta sulla base del valore di un field del json.

Per le classi che sono utilizzate anche in fase di invio di un messaggio, come le classi per la creazione di tastiere e delle loro componenti, è inoltre presente all'interno del companion object un **Encoder**, sempre messo a disposizione da Circe, utilizzato per convertire in maniera automatica o sulla base di uno specifico parametro un'istanza di tale classe in formato json.

L'entry point del sistema in fase di ricezione di un update è la classe **Update**, la quale è incaricata dell'avvio delle operazioni di derivazione semiautomatica, dopo aver convertito l'intero json in stile camelCase così da assicurare la corrispondenza tra filed del json e delle classi.

Configurazione simile è presenta anche in altre classi, come **TelegramMessage**, in quanto  potrebbero essere sate direttamente in fase di decodifica senza essere richiamate dalla classe Update e, nella quali quindi, è necessario mantenere la conversione in camelCase.

In questa sezione del progetto, quindi, il pattern maggiormente presente è certamente **Pimp my library**, per estendere le classi messe a disposizione dalla libreria Circe.

#### Package PPS19.scalagram.marshalling
Poiché tutti i campi all'interno dei json sfruttati da Telegram sono definiti seguendo il formato [snake_case](https://en.wikipedia.org/wiki/Snake_case), al contrario di quelle definite via codice che seguono quello [camelCase](https://en.wikipedia.org/wiki/Camel_case), il package marshalling è incaricato di eseguire le conversioni tra i due stili.

Si è deciso quindi di utilizzare due classi implicite che wrappassero le classi Decoder ed Encoder della libreria Circe, così da poter sfruttare in maniera comoda e immediata i metodi per la conversione contenuti al loro interno.\
Nello specifico, la classe DecoderOps contiene un metodo per la conversione in camelCase, in quanto per eseguire il decoding automatico è necessario che i field del json coincidano con quelli degli oggetti e, quindi, che vengano trasformati da snake_case a camelCase.\
Al contrario, la class EncoderOps, contiene un metodo per la conversione in snake_case, in modo che la codifica in json delle entità segua lo stile snake_case e sia accettata da Telegram.

Per portare a termine queste operazioni, si è sfruttata una funzione higher-order, la quale prende come parametro la funzione di trasformazione sulla stringa desiderata.\
Tali funzioni di trasformazione sono definite nel file package.scala e incluse all'interno di una classe CaseString, la quale wrappa la classe stringa, di modo che tali trasformazioni possano essere usate anche sulle singole stringhe e non necessariamente sui json, come accade per esempio nella codifica dell'URL.


### Implementazione - Mattia Rossi [Metodi]
### Attività di gruppo [Gruppo]
#### DSL

## 6. OPS
In questa sezione verranno descritti dettagliatamente gli aspetti relativi alla parte di **Operations** (Ops) implementati all'interno del progetto. Con Ops si intendono tutte quelle strategie finalizzate a semplificare ed automatizzare alcuni workflow relativi alla gestione del progetto.

L'utilizzo di un ambiente di Continuous Integration (CI), quale GitHub Actions consentirà di eseguire in maniera automatizzata alcune di queste procedure relative sia alla build automation del progetto, come compilazione, testing e quality assurance, ma anche relative alla gestione del repository ed alla pubblicazione degli artefatti.

Nelle successive sottosezioni verranno descritte tutte le procedure attuate proprio a questo fine.

### Automatic delivery e deployment [Rossi, Pistocchi]

In questa sezione verranno dettagliati gli aspetti relativi alla gestione automatizzata del repository ed alla pubblicazione degli artefatti sulla nota piattaforma Maven Central. Verrà dettagliato inoltre come l'utilizzo dell'integrator ci supporterà interamente durante questa fase.

- La prima domanda da porsi quando si deve pubblicare software è quella di stabilire la modalità di **versioning**. Per il seguente progetto si è deciso di utilizzare il semantic versioning.

- Per effettuare il versioning si sono utilizzati i **tag annotati** di Git.

- Si è sfruttata la possibilità di salvare alcuni **segreti** all'interno dell'integrator, per poi utilizzarli all'interno dei workflows. Si sono salvate ad esempio le credenziali per la pubblicazione su Maven Central.

- Una volta effettuato il tagging tramite il vcs, ci si avvale dell'integrator per effettuare la **pubblicazione della release**. A questo scopo è stato sviluppato un workflow in grado di reagire alla pubblicazione di un tag annotato. Vengono sfruttate due actions pubbliche in grado di creare e pubblicare la release all'interno del repository.

- Ci si avvale allo stesso modo del task precedente, mediante un ulteriore action pubblica, per effettuare la pubblicazione degli artefatti in maniere automatizzata sul **Maven Central** Repository.

### Build automation [Rossi, Pistocchi]

Questa sezione dettaglierà come è stata strutturata la fase di build automation all'interno dell'integrator GitHub Actions.

La fase di build è stata strutturata in un unica fase principale (job). Gli aspetti principali considerati in questa procedura sono i seguenti:

- L'utilizzo di una **matrice di build** ci ha permesso di effettuare riuso di codice per generalizzare sul **sistema operativo** sul quale viene eseguito il workflow di continuous integration e la configurazione della **versione di Java**.

- Si utilizza una action che ci permette di effettuare **l'upload** degli artefatti all'interno dell'ambiente di continuous integration. Questa action viene sfruttata per poter caricare l'output della build, solamente nel caso in cui essa fallisca. Questa proceduta viene effettuate quando si riceve un errore in fasi di compilazione o in fase di test.

- È stato inoltre schedulato un **chron job**, che ci permetterà di eseguire il workflow dedicato alla build automation settimanalmente.


### Licensing [Rossi]
La scelta della licenza da applicare al nostro sistema è ricaduta sulla **Apache License 2.0**, ideale per chi vuole sviluppare software open source con supporto a lungo termine.\
Si tratta di una licenza non copyleft che obbliga gli utenti a preservare l'informativa di diritto d'autore e d'esclusione di responsabilità nelle versioni modificate. In particolar modo i vincoli imposti e gli usi concessi sono i seguenti: 

| Permessi         | Limitazioni     | Condizioni                     |
|------------------|-----------------|--------------------------------|
| Uso commerciale  | Uso del marchio | Licenza e notice del copyright |
| Modifica         | Responsabilità  | Notifica dei cambiamenti       |
| Distribuzione    | Garanzia        |                                |
| Uso del brevetto |                 |                                |
| Uso privato      |                 |                                |

### QA [Boschi, Tumedei]
#### Testing
##### Testing automatizzato
##### Testing non automatizzato
##### Copertura dei test

## 7. Retrospective [Rossi, Optional[Tumedei]]
Retrospettiva (descrizione finale dettagliata dell'andamento dello sviluppo, del backlog, delle iterazioni; commenti finali)

### Sprint 1

L'obiettivo che il team si è posto al primo sprint è stato quello di stabilire le linee guida da seguire per lo sviluppo del progetto, con particolare enfasi su: design del dominio, architettura del sistema e definizione dei concetti chiave.

Un'altra importante attività svolta in questa fase è stata quella di effettuare il setup dell'ambiente di sviluppo, del repository e di una semplice pipeline di CI/CD, in modo da velocizzare la programmazione durante gli sprint successivi.

I task per questo sprint sono stati:
- **Domain Driven Design**: fase iniziale di knowledge crunching, comprendente anche la definizione dell'ubiquitous language, dei bounded context e delle user stories
- **Define project requirements**: stabilimento dei requisiti business, utente, funzionali, non funzionali e implementativi
- **Define architecture**: definizione del design per l'architettura del sistema, in questa fase con maggiore enfasi sul design complessivo e senza scendere troppo nel dettaglio
- **Environment setup**: creazione del progetto e del repository GitHub, setup dell'IDE IntelliJ Idea per lo sviluppo
- **Gradle automation process**: creazione di una prima versione del file `build.gradle.kts` per la gestione del progetto e delle sue dipendenze tramite appositi task
- **CI/CD setup**: creazione di una GitHub Action per automatizzare le operazioni di build e test
- **Setup project documentation**: creazione della board Trello del progetto e dei seguenti documenti: README, report, Project Backlog.

### Sprint 2

L'obiettivo per il secondo sprint è stato quello di fornire delle implementazioni basiche, ma già funzionanti, degli aspetti core del sistema, in particolare per quanto riguarda:
- Effettuare richieste alle Telegram API
- Eseguire il download degli update da Telegram
- Definire la logica di comportamento di un bot

I task per questo sprint sono stati:
- **Implement essential Telegram API methods**: iniziare lo sviluppo dei metodi wrapper per le chiamate alle Telegram API e delle classi modello per gli oggetti da esse restituiti
- **Define and implement bot logic**: definire una prima versione della classe Scalagram e dei relativi contenuti (Reactions, Middlewares, Scenes)
- **Develop a system to work in polling mode**: creare un Akka actor system per l'elaborazione degli update di un bot
- **Setup proper unit tests**: eseguire i task precedentemente elencati aderendo a una metodologia di sviluppo TDD
- **CI/CD Setup**: migliorare la matrice di espansione della GitHub action, passando un token diverso ad ogni cella in modo da evitare errori HTTP 429 da parte dei server di Telegram

### Sprint 3

Dal momento che il team aveva intenzione di iniziare lo sviluppo del DSL a partire dalla quarta settimana, l'obiettivo di questo sprint è stato quello di ottenere un sistema il più possibile funzionante, in modo da poter iniziare a wrappare le sue funzionalità con la sintassi del DSL nello sprint successivo.

I task per questo sprint sono stati:
- **Implement essential Telegram API methods**: terminare lo sviluppo dei metodi per l'utilizzo delle Telegram API e dei relativi model
- **Refactoring**: eseguire un refactoring dei metodi implementati in modo da facilitarne la futura estensibilità
- **Define and implement bot logic**: fornire una versione pienamente funzionante della classe Scalagram per la definizione della logica di comportamento dei bot
- **Develop a system to work in polling mode**: completare l'implementazione della modalità di polling per quanto riguarda il download degli update
- **CI/CD Setup**: installazione del plugin Scalafmt per la formattazione automatica del codice; miglioramento delle prestazioni della pipeline di CI

### Sprint 4

Avendo la prima versione funzionante della libreria, gli obiettivi principali di questo sprint sono stati: la definizione della sintassi del DSL, la creazione del primo bot di esempio basato su Scalagram e il rilascio della prima versione della libreria (in questa fase solamente attraverso l'upload di un artifact su GitHub).

I task per questo sprint sono stati:
- **Define and implement bot logic**: vista la prolissità riscontrata nella definizione dei trigger per le reaction del bot, si è deciso di implementare dei builder per facilitarne la creazione
- **Define a DSL for using the implemented solution**: definire la sintassi del DSL e iniziare a lavorare sul DSL per le tastiere del bot
- **Refactoring**: avendo terminato l'implementazione dei metodi delle Telegram API, si è eseguito un refactoring dei relativi modelli
- **Define and automate semantic versioning and releases**: installazione del plugin GitSemVer e pubblicazione della prima release
- **Create some bots to showcase the library**: creazione del primo bot basato su Scalagram

### Sprint 5

L'obiettivo di questo sprint verteva principalmente sull'estensione della sintassi del DSL appena creato e la creazione di un bot d'esempio che fosse in grado di sfruttarlo, per cui ci siamo concentrati anche sulla possibilità di poter sfruttare le API di Telegram, in questo caso unicamente per l'invio di un messaggio, a partire dal DSL.

I task per questo sprint sono stati:
- **Define a DSL for using the implemented solution**: estensione della sintassi del DSL per l'implementazione di reactions e middlewares
- **Define a DSL for interacting with the Telegram API**: a partire dal DSL sono state utilizzate le Telegram API per effettuare l'invio di messaggi 
- **Gradle automation process**: aggiunta e configurazione di Scalafmt come plugin per la formattazione del codice Scala 
- **Refactoring**: avendo proseguito con l'implementazione della sintassi del DSL, si è eseguito un refactoring delle reactions, della logica di funzionamento del bot e dei package dei modelli
- **Create some bots to showcase the library**: creazione di un primo bot di esempio che sfrutta la sintassi del DSL

### Sprint 6

In questa fase dello sviluppo ci siamo concentrati sugli aspetti riguardanti l'automazione del sistema, creando le basi per effettuare un rilascio automatico su Maven Central, oltre all'installazione e configurazione di diversi strumenti di supporto come Dependabot.\
Inoltre, in seguito all'installazione del plugin di scoverage, abbiamo creato nuovi test precedentemente mancanti e modificato quelli già presenti per evitare di usare le API di Telegram e quindi effettuare troppe richieste HTTP che spesso si traducevano in fallimenti dovuti a timeout. 

I task per questo sprint sono stati:
- **Release on Maven Central**: registrazione al servizio di Sonatype, creazione delle chiavi gpg per la firma digitale, aggiunta e configurazione del plugin necessario per la pubblicazione automatica su Maven Central
- **Gradle automation process**: configurazione di Dependabot, esportazione automatica di un artefatto su GitHub Actions contenente l'output della console se il job fallisce e aggiunta del plugin per la verifica della coverage dei test
- **Define a DSL for programming the bot logic**: estensione della sintassi del DSL per l'implementazione delle scene e aggiunta della possibilità di creare triggers che facciano match con qualsiasi messaggio
- **Setup proper unit tests**: aggiunta di ulteriori test per la verifica di parti del sistema altrimenti scoperte e modifica dei test già esistenti per evitare che utilizzino le Telegram API

### Sprint 7

Dal momento che la fase di sviluppo del sistema era ormai conclusa, abbiamo sfruttato questo sprint per la modifica di aspetti secondari come l'aggiunta di alcune regole particolarmente restrittive per la corretta compilazione del codice Scala (errori su import non utilizzati e warning).\
L'aspetto chiave di questo sprint è sicuramente stato la creazione di una moltitudine di test che, grazie all'analisi del plugin di scoverage, abbiamo aggiunto per ottenere un buon livello di copertura. 

I task per questo sprint sono stati:
- **Gradle automation process**: configurazione di ulteriori regole per la compilazione del codice Scala
- **Setup proper unit tests**: aggiunta di ulteriori test per raggiungere un livello di copertura del sistema sufficientemente elevato
- **Refactoring**: modifica della struttura dei package per permettere agli utenti della libreria di effettuare il minor numero di import necessari all'utilizzo

### Sprint 8

Durante l'ultimo sprint il team ha curato alcuni aspetti precedentemente preteriti e ci siamo assicurati che il sistema rispettasse i vincoli architetturali e di DDD precedentemente stilati.\
Una volta creati in via definitiva i bot di esempio, utili agli utenti che utilizzeranno la libreria, abbiamo lavorato sulla documentazione del progetto, in particolare sulla generazione della ScalaDoc e del report finale; ci siamo inoltre sincerati che gli aspetti di automazione del sistema fossero adeguati.

I task per questo sprint sono stati:
- **Create some bots to showcase the library**: creazione di bot di esempio che sfruttano vari aspetti della sintassi del DSL
- **Develop final report**: stesura della ScalaDoc e di parte del report finale
- **Release on Maven Central**: prima release del sistema su Nexus Repository
- **Domain Driven Design**: considerazioni finali su aspetti riguardanti il DDD adottato durante lo svolgimento del progetto
- **Define architecture**: considerazioni finali sull'architettura del sistema e aggiunta di aspetti di dettaglio 
- **Gradle automation process**: aggiunta e configurazione dei plugin per il versioning semantico e la creazione di un jar non eseguibile contenente la ScalaDoc 
- **CI/CD Setup**: refactor del workflow di GitHub Actions e creazione della Action per eseguire automaticamente il merge delle pull requests effettuate da Dependabot

## 8. Conclusioni [Gruppo]
### Sviluppi futuri
### Conclusioni
