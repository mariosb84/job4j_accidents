CREATE TABLE accidents (
   accident_id SERIAL PRIMARY KEY,
   accident_name TEXT,
   accident_text TEXT,
   accident_address TEXT,
   accidentTypes_id int references accidentTypes(accidentType_id),
   rules_id int references accidentRules(accidentRule_id)
);

