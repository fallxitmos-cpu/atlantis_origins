from PIL import Image
img = Image.open("src/main/resources/assets/atlantis_origins/textures/gui/catalytic_reactor.png")
px = img.load()

def find_rects(color, tol=0):
    w, h = img.size
    visited = [[False]*h for _ in range(w)]
    rects = []
    for y in range(h):
        for x in range(w):
            if not visited[x][y] and px[x, y] == color:
                # find extent
                x2 = x
                while x2 < w and px[x2, y] == color:
                    x2 += 1
                y2 = y
                while y2 < h and all(px[xx, y2] == color for xx in range(x, x2)):
                    y2 += 1
                for yy in range(y, y2):
                    for xx in range(x, x2):
                        visited[xx][yy] = True
                rects.append((x, y, x2-x, y2-y))
    return rects

colors = {
    "cyan": (86, 182, 194, 255),
    "dark_cyan": (60, 130, 140, 255),
    "orange": (232, 179, 95, 255),
    "brown": (180, 130, 60, 255),
    "gray": (80, 90, 110, 255),
}
for name, c in colors.items():
    rects = find_rects(c)
    print(name, c, rects)
