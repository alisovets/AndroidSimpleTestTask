## The task description.

Create simple application which must work in Landscape and Portrait orientations.

Interfaces must have 2 languages (English, Russian)

The main task is – get information from the Service (http://www.edsson.com/services.php), store this information in database and output information in two different views. You can choose different type (xml, json) using (?format=<type>)

At the top we must have refresh (animated) Image Button which used for update information from Service

# First view must have

    1. list of Items with minor details (name, description – 2 rows)

    * description can be more then 2 rows length, in this case it must have possibility to expand (we must see full description)

    2. each record must have link to detail information

    3. each record must have link to delete record in database. (After refresh all deleted records must be restored in internal database)

# Second view used for detailed information of current record, comments, if exists, must be hidden before you click on “+” link

* Name
* Code
* Description
* Price
*Details
* Display
* Processor
* Memory
* Hdd
* Other
* Comments
* Name
* Comment



