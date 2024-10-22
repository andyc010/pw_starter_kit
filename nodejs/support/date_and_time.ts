/* date_and_time.ts
Introduction:

This file contains a function and an enum that can be used to generate text that can be used
to show the date and time to be used as input in a test, or it can be used for files to be saved (such
as screenshots or logs).

This can be seen in the second test of the nodejs_starter.spec.ts file.
*/

export enum Usage {
    file = 0,
    text = 1,
}

export function displayCurrentDateAndTime(usage_type: Usage): string {
    // Get the current date & time, expressed in millseconds elapsed since January 1, 1970 & instantiate a Date object
    const current_time_elapsed = Date.now();
    const current_date_and_time = new Date(current_time_elapsed);

    let current_month = current_date_and_time.getMonth() + 1;
    let current_date = current_date_and_time.getDate();
    let current_full_year = current_date_and_time.getFullYear();
    let current_hour = current_date_and_time.getHours();
    let current_minutes = current_date_and_time.getMinutes();
    let current_seconds = current_date_and_time.getSeconds();
    
    if(usage_type === Usage.file) {
        return(appendZeroCharacter(current_full_year) + appendZeroCharacter(current_month) + appendZeroCharacter(current_date) + '_' 
                + appendZeroCharacter(current_hour) + appendZeroCharacter(current_minutes) + appendZeroCharacter(current_seconds));
    } else {
            return(appendZeroCharacter(current_full_year) + '/' + appendZeroCharacter(current_month) + '/' + appendZeroCharacter(current_date) + ' ' 
            + appendZeroCharacter(current_hour) + ':' + appendZeroCharacter(current_minutes) + ':' + appendZeroCharacter(current_seconds));
    }
}

function appendZeroCharacter(value: number): string {
    if(value < 10) {
        return('0' + value.toString());
    } else {
        return(value.toString());
    }
}
