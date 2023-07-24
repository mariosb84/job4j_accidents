CREATE TABLE link (
   link_id SERIAL PRIMARY KEY,
   rules_id int references accidentRules(accidentRule_id),
   accidentLink_id int references accidents(accident_id)
);