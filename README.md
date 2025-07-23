# Demo Todo List con Oracle Coherence e Spring Boot

Applicazione di esempio Todo List sviluppata con Spring Boot e Oracle Coherence, che sfrutta una cache distribuita per la gestione scalabile dei task.

## Caratteristiche
- CRUD di task (creazione, lettura, aggiornamento, cancellazione)
- Cache distribuita con Oracle Coherence per alta disponibilità e performance
- Operazioni batch e processamento distribuito con EntryProcessor
- API REST documentate con Swagger/OpenAPI

## Avvio
1. Clona il repository:
   ```bash
   git clone https://github.com/tuo-utente/demo-todo-coherence.git
   cd demo-todo-coherence
   ```
2. Avvia l'app con Maven o IDE:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Al primo avvio vengono caricati automaticamente 1000 task di esempio in cache.
4. Consulta la documentazione API su:  
   `http://localhost:8080/swagger-ui/index.html`

## Tecnologie
- Java 21
- Spring Boot
- Oracle Coherence
- Springdoc OpenAPI (Swagger UI)

## Funzionalità principali
- Endpoint REST per la gestione dei task
- Filtri e query avanzate su Coherence
- Operazioni di completamento massivo dei task tramite processor distribuiti
- Cancellazione selettiva e batch dei task completati

---

🚨 **ATTENZIONE:**  
Questa applicazione è stata sviluppata come **demo e test** per esplorare e comprendere l’uso di Oracle Coherence con Spring Boot.  
**❌ NON è pensata per ambienti di produzione senza opportune revisioni, miglioramenti di sicurezza e scalabilità.**
