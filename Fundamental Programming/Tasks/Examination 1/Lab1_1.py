print("Ticket types:\n1. Budget ticket - 500kr\n2. Economy ticket - 750kr\n3. VIP ticket - 2000kr\n")
addon_selection = "1. Add bag (max 1)\n2. Add meal (max 1)\n3. Remove bag\n4. Remove meal\n5. Finalize ticket"

while True:
    ticket_price = 0
    ticket_input = int(input("Select your ticket type: "))

    if ticket_input == 1:
        ticket_price += 500
        break
    elif ticket_input == 2:
        ticket_price += 750
        break
    elif ticket_input == 3:
        ticket_price += 2000
        break

print("\nCurrently you have:\n\t0 bag(s) registered\n\t0 meal(s) registered\n")
print("Here are your options:\n" + addon_selection)
addon_input = int(input("Your choice: "))

while addon_input <= 5:
    bag_r = 0
    meal = 0
    bag_price = 0
    meal_price = 0

    if addon_input == 0:
        addon_input = int(input("Your choice: "))
    if addon_input == 1:
        bag_r += 1
        bag_price += 200
        print(f"\nCurrently you have:\n\t{bag_r} bag(s) registered\n\t{meal} meal(s) registered\n")
        print("Here are your options:\n" + addon_selection)
        addon_input = int(input("Your choice: "))

    if addon_input == 2:
        meal += 1
        meal_price += 150
        print(f"\nCurrently you have:\n\t{bag_r} bag(s) registered\n\t{meal} meal(s) registered\n")
        print("Here are your options:\n" + addon_selection)
        addon_input = int(input("Your choice: "))

    if addon_input == 3:
        if bag_r == 1:
            bag_r += (-1)
            bag_price += (-200)
        print(f"\nCurrently you have:\n\t{bag_r} bag(s) registered\n\t{meal} meal(s) registered\n")
        print("Here are your options:\n" + addon_selection)
        addon_input = int(input("Your choice: "))

    if addon_input == 4:
        if meal == 1:
            meal += (-1)
            meal_price += (-150)
        print(f"\nCurrently you have:\n\t{bag_r} bag(s) registered\n\t{meal} meal(s) registered\n")
        print("Here are your options:\n" + addon_selection)
        addon_input = int(input("Your choice: "))

    if addon_input == 5:
        total = (ticket_price + bag_price + meal_price)

        ticket_str = str(ticket_price)
        ticket_price_align = ticket_str.rjust(4)

        total_str = str(total)
        total_align = total_str.rjust(4)

        bag_str = str(bag_price)
        bag_align = bag_str.rjust(4)

        meal_str = str(meal_price)
        meal_align = meal_str.rjust(4)

        z = " "
        if meal == 0 and bag_r == 0:
            print("\nReceipt:\nTicket" + z + ":" + z + ticket_price_align + "kr")
            print(8 * z + "-------\nTotal  : " + ticket_price_align + "kr")

        elif meal == 1 and bag_r == 0:
            print("\nReceipt:\nTicket" + z + ":" + z + ticket_price_align + "kr")
            print("Meal" + 3 * z + ":" + z + meal_align + "kr")
            print(8 * z + "-------\nTotal  : " + total_align + "kr")

        elif meal == 0 and bag_r == 1:
            print("\nReceipt:\nTicket" + z + ":" + z + ticket_price_align + "kr")
            print("Bag" + 4 * z + ":" + 1 * z + bag_align + "kr")
            print(8 * z + "-------\nTotal  : " + total_align + "kr")

        elif meal == 1 and bag_r == 1:
            print("\nReceipt:\nTicket" + z + ":" + z + ticket_price_align + "kr")
            print("Bag" + 4 * z + ":" + 1 * z + bag_align + "kr\nMeal" + 3 * z + ":" + z + meal_align + "kr")
            print(8 * z + "-------\nTotal  : " + total_align + "kr")
        break
