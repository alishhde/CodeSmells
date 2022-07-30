# Seyedali Shohadaalhosseini - Alishhde

# Problem
class Shop():
    def __init__(self, category, product, priceRange):
        self.category = str(category) 
        self.product = str(product)
        self.priceRange = float(priceRange)

    def addCategory(self):
        ...
    
    def findRequested(self, findme):
        ...


# Solution
class Shop():
    def __init__(self, category, product, priceRange, quantity):
        self.category = str(category) 
        self.product = str(product)
        self.priceRange = float(priceRange)
        self.quantity = str(int(quantity)) # Saving with string type

    def addCategory(self):
        ...
    
    def findRequested(self, findme):
        ...

