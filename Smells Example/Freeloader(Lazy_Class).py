# Seyedali Shohadaalhosseini - Alishhde

from os import lstat


class Items():
    def __init__(self, lst) -> None:
        self.lst = lst

    def lastItem(self):
        return self.lst[-1]