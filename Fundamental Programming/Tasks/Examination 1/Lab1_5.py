counter = 0
position_x = 7
position_y = 3

print("You have been placed in a pitch-black labyrinth, without knowing if there is a way out or not. Maybe there are "
      "traps? The only option available is to walk in a random direction and hope for the best, hope that you do not "
      "walk into a wall, or even worse, that you walk in circles. But hurry up, you only have so many moves… ")

print("""
To move around use:
w = up
a = left
s = down
d = right
""")


def ouch():
    print("Ouch! I can not walk through walls…")


grid = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],  # row 0
    [0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0],  # row 1
    [0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0],  # row 2
    [0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0],  # row 3
    [0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0],  # row 4
    [0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0],  # row 5
    [0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0],  # row 6
    [0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0],  # row 7
    [0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0],  # row 8
    [0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0],  # row 9
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]  # filler row
]

while counter < 20:
    direction_input = str(input("Enter direction: "))
    up_or_down = 1
    right_or_left = 1

    if direction_input == "w":
        if grid[position_x - up_or_down][position_y] == 1:
            position_x -= 1
            counter += 1
        elif grid[position_x - up_or_down][position_y] == 0:
            ouch()

    elif direction_input == "s":
        if grid[position_x + up_or_down][position_y] == 1:
            position_x += 1
            counter += 1
        elif grid[position_x + up_or_down][position_y] == 0:
            ouch()

    elif direction_input == "a":
        if grid[position_x][position_y - right_or_left] == 1:
            position_y -= 1
            counter += 1
        elif grid[position_x][position_y - right_or_left] == 0:
            ouch()

    elif direction_input == "d":
        if grid[position_x][position_y + right_or_left] == 1:
            position_y += 1
            counter += 1
        elif grid[position_x][position_y + right_or_left] == 0:
            ouch()

    if position_x == 9 and position_y == 4 or position_x == 1 and position_y == 7:
        print("Oh no, a trap!")
        position_x = 7
        position_y = 3
    elif position_x == 3 and position_y == 10:
        print("Oh no, a trap!")
        position_x = 7
        position_y = 3

    if position_x == 1 and position_y == 9:
        print("You survived! Well done adventurer!")
        break

    if position_x == 1 and position_y == 5 or position_x == 6 and position_y == 9:
        print("A chocolate bar, I feel stronger.")
        counter -= 20

    if counter == 20:
        print("Game over! You did not reach the exit in time.")
