CREATE TABLE rulesSet (
   id serial PRIMARY KEY,
   accident_id int,
   rules_id int not null REFERENCES accidentRules(id)
);