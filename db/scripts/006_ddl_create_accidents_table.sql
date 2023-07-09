CREATE TABLE accidents (
   id SERIAL PRIMARY KEY,
   name TEXT,
   text TEXT,
   address TEXT,
   accidentTypes_id int references accidentTypes(id),
   rules_id int references rulesSet(id)
);

