# JEE

Bienvenu(e) sur le dépôt du projet JEE 2025/2026 de CY Tech.

## Sommaire
- [JEE](#jee)
  - [Sommaire](#sommaire)
  - [Base de données](#base-de-données)

## Base de données

Table:
- permission (#id, description)
- user (#id, firstname, lastname, *contacts?*)
- user_permission (#id, user_id, permission_id)
- department (#id, name)
- user_department (#id, department_id, user_id, role(**ENUM**))
- project (#id, name, status)
- user_project (#id, project_id, user_id, role(**ENUM**))
- payslip (#id, user_id, hour, wage, bonus, malus, date)

