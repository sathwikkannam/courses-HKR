print("Welcome to the Money Bag Transport Calculator (M.B.T:C)")
print("-------------------------------------------------------")

big = 0
med = 0
small = 0

BL = 80
ML = 50
SL = 20

volume_input = int(input("What is the volume of the truck (>=100L): "))

while volume_input < 100:
    volume_input = int(input("What is the volume of the truck (>=100L): "))

if volume_input >= 100:
    while volume_input >= 80:
        big += volume_input // BL
        break

    remaining_volume = volume_input - (big * BL)

    while remaining_volume >= 50:
        med += remaining_volume // ML
        break

    remaining_volume = remaining_volume - (med * ML)

    while remaining_volume >= 20:
        small += remaining_volume // SL
        break

price = ((big * 60000) + (small * 10000) + (med * 30000))
space_left = volume_input - ((big * 80) + (small * 20) + (med * 50))

print(f"\nPacking plan\n------------\n{big} big bags\n{med} medium bags\n{small} small bags\n")
print(f"Space left : {space_left}L")
print(f"Total Value: {price}kr")
