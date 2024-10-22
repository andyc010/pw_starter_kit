from enum import Enum
from datetime import datetime

class Usage(Enum):
    TEXT = 1
    FILENAME = 2

def return_date_and_time(usage: Usage):
    todays_date_and_time = datetime.now()

    if(usage == Usage.TEXT):
        return append_zero_character(todays_date_and_time.month) + "/" + append_zero_character(todays_date_and_time.day) + "/" + append_zero_character(todays_date_and_time.year) + " " + \
            append_zero_character(todays_date_and_time.hour) + ":" + append_zero_character(todays_date_and_time.minute) + ":" + append_zero_character(todays_date_and_time.second)
    else:
        return append_zero_character(todays_date_and_time.year) + append_zero_character(todays_date_and_time.month) + append_zero_character(todays_date_and_time.day) + "_" + \
            append_zero_character(todays_date_and_time.hour) + append_zero_character(todays_date_and_time.minute) + append_zero_character(todays_date_and_time.second)

def append_zero_character(value: int):
    if(value < 10):
        return("0" + str(value))
    else:
        return str(value)