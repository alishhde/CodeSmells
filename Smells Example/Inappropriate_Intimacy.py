# Seyedali Shohadaalhosseini - Alishhde 

# Problem
class Info():
    def __init__(self, name, ID, Date, Address) -> None:
        self.name = name
        self.ID = ID
        self.Date = Date
        self.Address = Address # address is a dictionary e.g. {"Country": "IRAN", "City":"Babolsar", "Other":"Talebi St. No. 324"}

class Cient(Info):
    def __init__(self, name, ID, Date, Address) -> None:
        super().__init__(name, ID, Date, Address)
    
    def printClientInfo(self):
        print(self.name)
        print(self.ID)
        print(self.Date)
        print(self.Address)

# Solution
class Info():
    def __init__(self, name, ID, Date, Address) -> None:
        self.name = name
        self.ID = ID
        self.Date = Date
        self.Address = Address # address is a dictionary e.g. {"Country": "IRAN", "City":"Babolsar", "Other":"Talebi St. No. 324"}
    
    def clientInfo(self):
        return self.name, self.ID, self.Date, self.Address

class Cient(Info):
    def __init__(self, name, ID, Date, Address) -> None:
        super().__init__(name, ID, Date, Address)
    
    def printClientInfo(self):
        print(self.clientInfo(), sep="\n")