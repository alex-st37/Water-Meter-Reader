# Water-Meter-Reader
Living in an apartment building or in a condominium means certain obligations for the residents, including presenting the monthly water consumption to the administrator of the owners' association and keepeng the record of the water consumption readings.

The following program comes in the aid of this category of persons, in the sense that it offers them the possibility to indefinitely store water indexes and, most importantly, to calculate at any time the current consumption of cold and hot water and to generate a ready-to-print file so that it can be handed immediately over to the administration.

This way, the residents will no longer need to keep records on paper, to manually calculate the monthly water consumption and to write by hand each month the flyer for the administrator.

## Usage Features
1. Easilly adding new water meter readings;
2. The history of water meter records is saved forever and is accessible anytime;
3. Displaying the current water consumption;
4. Generating a ready-to-print text file (flyer) containing the curent water consumption and details about the current water meter reading and the last one;
5. The ability to delete the last water meter reading saved in case the user made mistakes regarding the date of the reading or the level indicated by the watermeter;
6. The option to clear the whole list of recorded water indexes.

## Technical Features
1. The user's input is checked whenever he gives a command, so that any possible exception that could crush the program are avoided.
2. When adding a new watermeter reading, the program does the following verifications:
    1) It checkes if the input for date is in the format "dd MM yyyy". Even though it's not showed in the app text, the program allows intuitive input in the format "d MM yyyy" or "dd M yyyy";
    2) If the date entered by the user respects one of the formats listed above, than it is compared to the last date saved in the history of water meter readings, if it exists. For logical reasons, a valid new date needs to be set in time after the moment of the previous water meter reading.
    3) Regarding the input for the water meter reading, firstly the program verifies if the input from the user is of type double.
    4) Secondly, for logical considerations, the program tests if the new water meter index is higher than the previous one recorded. If the test is negative, that means that maybe the input from the user is wrong or the last recorded water meter reading is incorrect. The application offers solutions for both of the cases - the user can reenter the correct input or delete the last recorded water meter index.
3. All the data entered by the user is saved in a text file in key moments of the life cycle of the application - e.g. after adding a new reading, after deleting an old one, before wuiting the program etc.
4. The program permits the user to delete the last recorded water meter reading only once, until a new water meter reading is added. The reason for this restriction is that a data registry in which all data could be modified would no longer be reliable.
5. The choice to delete the last element of the water meter readings history is saved in a text file and reloaded each time the application is restarted, so the "deleting the last element only once" feature can not be fooled by exiting and restarting the program.
6. FLyeeeeeeeer................................

