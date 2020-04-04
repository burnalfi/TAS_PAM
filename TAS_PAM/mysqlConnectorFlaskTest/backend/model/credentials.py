from flask import jsonify

class Credentials:

    def __init__(self, id, name="", number="", secondnumber=""):
        self.id = id        
        self.name = name
        self.number = number    
        self.secondnumber = secondnumber

    def get_json(self):
        return jsonify(self.id, self.name, self.number, self.secondnumber)


    def get_dict(self):
        return {
            "id"  : self.id,
            "name"  : self.name,
            "number" : self.number,
            "secondnumber"    : self.secondnumber,
        }
    