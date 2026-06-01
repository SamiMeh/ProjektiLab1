# AutoShkolla Pro

Sistemi i menaxhimit për autoshkollë (projekt universitar).

## Si funksionon

- **Backend:** Spring Boot + MariaDB (`src/main/java`)
- **Frontend:** React + Bootstrap (`frontend/`) — pas `npm run build` shkon në `src/main/resources/static/`

## Nisja

1. MariaDB me databazën `autoshkolla` (shiko `application.properties`)
2. Build UI (një herë ose pas ndryshimeve në React):

```bash
cd frontend
npm install
npm run build
```

3. Nis serverin:

```bash
./gradlew bootRun
```

4. Hap: http://localhost:8081/login

## Rollet

| Roli | Rruga |
|------|--------|
| Kandidat | `/kandidat` |
| Admin | `/admin` (10 module me CRUD) |
| Instruktor | `/admin` (orë, provime, orare) |

`package.json` është vetëm për ndërtimin e faqes React — nuk nevojitet në prodhim nëse `static/assets` është builduar tashmë.
