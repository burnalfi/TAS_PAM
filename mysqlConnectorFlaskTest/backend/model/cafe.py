from flask import jsonify

class Cafe:

    def __init__(self, establisment="", hours="", phone="", address="", description=""):
        self.establisment = establisment        
        self.hours = hours
        self.phone = phone    
        self.address = address
        self.description = description 

    def get_json(self):
        return jsonify(self.establisment, self.hours, self.phone, self.address, self.description)


    def get_dict(self):
        return {
            "establisment"  : self.establisment,
            "hours"  : self.hours,
            "phone" : self.phone,
            "address"    : self.address,
            "description" : self.description
        }