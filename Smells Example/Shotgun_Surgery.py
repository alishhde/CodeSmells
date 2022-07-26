# Seyedali Shohadaalhosseini - Alishhde

# Smell Found
class Students():
    def __init__(self, name, Lname, age, subject, ID) -> None:
        self.STName = name
        self.STLname = Lname
        self.STAge = age
        self.STSubject = subject
        self.STID = ID

    def clubRegistration(self):
        self.clubRegisteredList = list()

        if self.STAge < 18:
            print("Student cannot register to club")
            return False
        else:
            # Enter student name to the registered list.
            self.registeredList.append([self.STName, self.STLname])
            return True

    def libraryRegistration(self):
        self.libraryRegisteredList = list()
        if self.STAge < 18:
            print("Student cannot register to library")
            return False
        else:
            # Enter student ID to the registered list.
            self.libraryRegisteredList.append(self.STID)
            return True

    def courseRegitration(self):
        self.courseRegisteredList = list()
        if self.STAge < 18:
            print("Student cannot register to library")
            return False
        else:
            # Enter student name, lastname, ID to the registered list.
            self.courseRegisteredList.append([self.STName, self.STLname, self.STID])
            return True

# Smell Solved
class Students():
    def __init__(self, name, Lname, age, subject, ID) -> None:
        self.STName = name
        self.STLname = Lname
        self.STAge = age
        self.STSubject = subject
        self.STID = ID
    
    def StudentageUnderThreshold(self, Threshold=18):
        if self.STAge < Threshold:
            print("Student cannot register to club")
            return True
        # Student have permission to register.
        return False

    def clubRegistration(self):
        self.clubRegisteredList = list()
        if self.StudentageUnderThreshold():
            print("Student cannot register to club")
            return False
        else:
            # Enter student name to the registered list.
            self.registeredList.append([self.STName, self.STLname])
            return True

    def libraryRegistration(self):
        self.libraryRegisteredList = list()
        if self.StudentageUnderThreshold():
            print("Student cannot register to club")
            return False
        else:
            # Enter student ID to the registered list.
            self.libraryRegisteredList.append(self.STID)
            return True

    def courseRegitration(self):
        self.courseRegisteredList = list()
        if self.StudentageUnderThreshold():
            print("Student cannot register to club")
            return False
        else:
            # Enter student name, lastname, ID to the registered list.
            self.courseRegisteredList.append([self.STName, self.STLname, self.STID])
            return True