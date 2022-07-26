# Seyedali Shohadaalhosseini - Alishhde

x = [1, 2, 3, 4, 5]
# smell code
def summationOf(x):
    """ Where x is a list of numbers. """
    s = 0 
    for number in x:
        s += number
    return s

# smell solved with one line of coding
s = sum(x)