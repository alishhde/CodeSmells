# Seyedali Shohadaalhosseini - Alishhde 

# Problem
class Client:
    def __init__(self) -> None:
        ...

    def zipc():
        return Address.zipCode()

class Address:
    def __init__(self) -> None:
        ...
    
    def zipCode():
        return "A8Sj093L"

class Application:
    def __init__(self) -> None:
        ...
        
    applicationZipCode = Client.zipc()

# Solution
class Client:
    def __init__(self) -> None:
        ...

    def zipc():
        return Address

class Address:
    def __init__(self) -> None:
        ...
    
    def zipCode():
        return "A8Sj093L"

class Application:
    def __init__(self) -> None:
        ...
        
    applicationZipCode = Client.zipc().zipCode()