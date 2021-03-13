# ScalaGram - Report

## Development process [Rossi]
Processo di sviluppo adottato (modalità di divisione in itinere dei task, meeting/interazioni pianificate, modalità di revisione in itinere dei task, scelta degli strumenti di test/build/continuous integration)

### Divisione dei task
### Meeting ed interazioni
### Strumenti utilizzati

## Requirements [Gruppo]
Requisiti (delle varie tipologie, ossia: 1) business, 2) utente, 3) funzionali, 4) non funzionali, 5) di implementazione)

### Business
#### Knowledge crunching
#### Ubiquitous language
### Utente
### Funzionali
#### DSL
### Non funzionali
### Implementazione

## Architectural Design [Gruppo - Pistocchi]
Design architetturale (architettura complessiva, descrizione di pattern architetturali usati, componenti del sistema distribuito, scelte tecnologiche cruciali ai fini architetturali -- corredato da pochi ma efficaci diagrammi)

### Bounded context [Pistocchi]
### DSL e user story [Pistocchi]

## Design Detail
Design di dettaglio (scelte rilevanti, pattern di progettazione, organizzazione del codice -- corredato da pochi ma efficaci diagrammi)

### Scelte rilevanti [Boschi]
### Organizzazione del codice [Rossi, Tumedei]

## Implementation
Implementazione (per ogni studente, una sotto-sezione descrittiva di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti non già presenti nel design)

### Implementazione - Gianni Tumedei [Logica bot]
### Implementazione - Francesco Boschi [Modelli, marshalling]
### Implementazione - Mattia Rossi [Metodi] 
### Attività di gruppo [Gruppo]
#### DSL 

## OPS
In questa sezione verranno descritti dettagliatamente gli aspetti relativi alla parte di **Operations** (Ops) implementati all'interno del progetto. Con Ops si intendono tutte quelle strategie finalizzate a semplificare ed automatizzare alcuni workflow relativi alla gestione del progetto.

L'utilizzo di un ambiente di Continuous Integration (CI), quale GitHub Actions consentirà di eseguire in maniera automatizzata alcune di queste procedure relative sia alla build automation del progetto, come compilazione, testing e quality assurance, ma anche relative alla gestione del repository ed alla pubblicazione degli artefatti.

Nelle successive sottosezioni verranno descritte tutte le procedure attuate proprio a questo fine.

### Automatic delivery e deployment [Rossi, Pistocchi]
- tagging
- publish release
- publish on maven central
### Build automation [Rossi, Pistocchi]

Questa sezione dettaglierà come è stata strutturata la fase di build automation all'interno dell'integrator GitHub Actions.

La fase di build è stata strutturata in un unica fase principale (job). Gli aspetti principali considerati in questa procedura sono i seguenti:

- L'utilizzo di una **matrice di build** ci ha permesso di effettuare riuso di codice per generalizzare sul **sistema operativo** sul quale viene eseguito il workflow di continuous integration e la configurazione della **versione di Java**.

- Si utilizza una action che ci permette di effettuare **l'upload** degli artefatti all'interno dell'ambiente di continuous integration. Questa action viene sfruttata per poter caricare l'output della build, solamente nel caso in cui essa fallisca. Questa proceduta viene effettuate quando si riceve un errore in fasi di compilazione o in fase di test.

- È stato inoltre schedulato un **chron job**, che ci permetterà di eseguire il workflow dedicato alla build automation settimanalmente.


### Licensing [Rossi]
### QA [Boschi, Tumedei]
#### Testing 
##### Testing automatizzato
##### Testing non automatizzato
##### Copertura dei test

## Retrospective [Rossi, Optional[Tumedei]]
Restrospettiva (descrizione finale dettagliata dell'andamento dello sviluppo, del backlog, delle iterazioni; commenti finali)

### Sprint 1 
### Sprint 2
### Sprint 3
### Sprint 4
### Sprint 5
### Sprint 6
### Sprint 7
### Sprint 8

## Conclusioni [Gruppo]
### Sviluppi futuri
### Conclusioni
