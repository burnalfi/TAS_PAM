from flask import jsonify

class User:

    def __init__(self, username="", password=""):
        self.username = username        
        self.password = password

    def get_json(self):
        return jsonify(self.username, self.password)


    def get_dict(self):
        return {
            "username"  : self.username,
            "password"  : self.password
        }