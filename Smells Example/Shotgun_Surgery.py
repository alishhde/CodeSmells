# Seyedali Shohadaalhosseini - Alishhde
from tkinter.font import BOLD
from unittest import result
from xmlrpc.client import Boolean, boolean


class Students():
    def __init__(self, name, Lname, age, subject, ID) -> None:
        self.STName = name
        self.STLname = Lname
        self.STAge = age
        self.STSubject = subject
        self.STID = ID

    def clubRegistration(self):
        result = Boolean()
        self.registeredList = list()

        if self.STAge < 18:
            print("Student cannot register to club")
            return False
        else:
            # Enter student name to the registered list.
            self.registeredList.append([self.STName, self.STLname])
            return True

    def libraryRegistration(self):
        ...

    def courseRegitration(self):
        ...