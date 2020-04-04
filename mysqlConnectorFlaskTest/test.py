import requests

data1 = {'id':'skh12', 'name':'Sakuhiro', 'number':32, 'secondnumber':44}

r = requests.post("http://localhost:5000/credential/insert", data = data1)
print(r.status_code)

