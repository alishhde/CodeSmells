import requests
import lxml
from bs4 import BeautifulSoup

url = "https://github.com/apache/tomcat/blob/a9c1a0661198d9ba37c1facd8385fe05d538c4ad/java/org/apache/el/parser/ELParser.java/#L140-L168"
headers = {
  'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36 QIHU 360SE'
}
f = requests.get(url, headers = headers)

soup = BeautifulSoup(f.content,'lxml')
# print(soup)
numberOfLink = True

num = 1
file_id = 532
while numberOfLink:
    file = open(f"row{num}-id{file_id}.java", "w")
    t = True
    for index in range(140, 169):
        if t:
            t = False
            pass
        else:
            file.write("\n")
        line = soup.find('td', {'id':f'LC{index}'}).get_text()
        file.write(line)
    file.close()
    # print(line, flush=True)
    num += 1
    break