from PIL import Image
img = Image.open("src/main/resources/assets/atlantis_origins/textures/gui/catalytic_reactor.png")
print(img.size)
# Find non-background colored pixels (background approx #29364c?)
# Let's sample unique colors
from collections import Counter
colors = Counter(img.getdata())
for c, n in colors.most_common(20):
    print(c, n)
