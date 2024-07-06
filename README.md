### Hospital Management System API

#### Routes and Strategies

1. *Did you use the same type of route to update patient information as to update an employee's department?*

   No, I did not use the same type of route. 

   - *Employee Department Update:* Utilized a PUT request (PUT /api/doctors/:employee_id/department) to update an employee's department. This approach involves replacing the entire resource (employee record) with the updated information.
   
   - *Patient Information Update:* Employed a PATCH request (PATCH /api/patients/:patient_id) to update patient information. This method allows for partial updates, enabling modifications to specific fields of the patient record without replacing the entire resource.

2. *Why did you choose the selected strategy?*

   - *PUT for Employee Department:* Chosen because updating an employee's department typically involves updating the entire department field. This method ensures clarity and completeness in updating the resource.
   
   - *PATCH for Patient Information:* Selected to allow flexibility in updating patient details. Often, not all fields need updating simultaneously. PATCH enables efficient updates by focusing on specific fields, minimizing data transfer and potential overwrites.

3. *What are the advantages and disadvantages of the strategies you chose for creating these routes?*

   - *PUT (Employee Department Update):*
     - Advantages: Ensures atomic updates, straightforward implementation, and clear intent. Suitable for full resource replacement.
     - Disadvantages: May result in overwriting existing data if not handled carefully, potentially transferring unnecessary data.

   - *PATCH (Patient Information Update):*
     - Advantages: Allows selective updates, reducing data transfer and minimizing the risk of unintentional overwrites. Offers flexibility in managing partial updates.
     - Disadvantages: Requires careful implementation to handle partial updates correctly. Increased complexity in ensuring data consistency.

4. *What is the cost-benefit between using PUT and PATCH?*

   - *PUT:* Best suited for scenarios where complete resource replacement is required, ensuring atomic updates and simplicity in implementation. However, it may lead to unnecessary data transfer and overwrite risks.

   - *PATCH:* Ideal for scenarios where partial updates are frequent or desired, optimizing data transfer and reducing the risk of unintentional overwrites. Offers flexibility but requires careful implementation to maintain data integrity.
