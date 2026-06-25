import zipfile, os
base = "/e/server/1.21.10基础整合包/1.21.10/.minecraft/libraries/net/neoforged/neoforge/21.1.228/neoforge-21.1.228-universal.jar"
with zipfile.ZipFile(base) as z:
    for n in z.namelist():
        if 'worldgen/configured_feature/ore_iron' in n or 'worldgen/configured_feature/ore_dirt' in n or 'worldgen/configured_feature/ore_gravel' in n:
            print('===', n, '===')
            print(z.read(n).decode('utf-8')[:2000])
