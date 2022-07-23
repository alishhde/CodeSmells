# Seyedali Shohadaalhosseini - Alishhde
#  Where n is the multiplier and x is a list 
def CalculateSumOfMultiplyOf(n, x): 
    nums_list = []
    for i in x:
        nums_list.append(n*i)
    return sum(nums_list)

# Duplicated function
def calculateSumOfMultiplyOf2(n, x):
    for i in x:
        nums += n*i
    return nums