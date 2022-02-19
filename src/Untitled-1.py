import math

def c(n, k):
    top = math.factorial(n)
    bottom = math.factorial(k) * math.factorial(n - k)
    print(top / bottom)

c(116, 4)

def p(n, k):
    top = math.factorial(n)
    bottom = math.factorial(n - k)
    print(top / bottom)

p(116, 4)

def multi(n, k):
    top = math.factorial(n + k - 1)
    bottom = math.factorial(k) * math.factorial(n - 1)
    print(top / bottom)

multi(116, 4)