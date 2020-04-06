import requests



# r = requests.post("http://localhost:5000/cafe/get/establishment", headers = {'establisment' : 'Enjoyment'})
# print(r.json()['address'])

# data = {'establisment' : 'Funfun', 'hours' : '12.00 - 22.00', 'phone' : '+628920167734', 'address' : 'Jl. Wibowo Sunaryo No. 9', 'description' : "It's not fun without Funfun"}

# r = requests.post("http://localhost:5000/cafe/insert", data = data)
# print(r)

# r = requests.post("http://localhost:5000/user/get/username", headers = {'username' : 'adinda'})
# print(r.json())

data = {'username' : 'mistamiyagi', 'password' : 'waxonwaxoff'}

r = requests.post("http://localhost:5000/user/insert", data = data)
print(r)