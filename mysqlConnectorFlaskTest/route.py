import os

from backend.controller.credentials import CredentialController 
from backend.controller.cafe import CafeController
from backend.controller.user import UserController

from backend.model.credentials import Credentials
from backend.model.cafe import Cafe
from backend.model.user import User

from database import Database

from flask import Flask, request, jsonify 

app = Flask(__name__)
app.secret_key = os.urandom(24)

app.config['SECRET_KEY'] = '414c61523ea509b476bdba060a49d3a3'

db = Database(user="root", password="", host="127.0.0.1", database="flasktest")

cc = CredentialController(db)
cac = CafeController(db)
uc = UserController(db)

#-----------Credential Section----------

@app.route("/credential/get/id", methods=['POST'])
def get_credential_by_id(): 
    return cc.get_by_id(request.headers.get('id')).get_json()
    

@app.route("/credential/get/all", methods=['POST'])
def get_credential_all():
    return jsonify({"credentials" : [i.get_dict() for i in cc.get_all()]})

@app.route("/credential/insert", methods=["POST"])
def insert_credential():
    cc.insert_credential(
        Credentials(
            id                  = request.form.get('id'),
            name                = request.form.get('name'),
            number              = request.form.get('number'),
            secondnumber        = request.form.get('secondnumber')
        )
    )
    return "CREDENTIAL INSERTED"


#----------Cafe Sections---------------

@app.route("/cafe/get/establisment", methods=['POST'])
def get_cafe_by_establishment(): 
    return cac.get_by_est(request.headers.get('establisment')).get_json()

@app.route("/cafe/get/all", methods=['POST'])
def get_credential_all():
    return jsonify({"cafe" : [i.get_dict() for i in cac.get_all()]})

@app.route("/cafe/insert", methods=["POST"])
def insert_credential():
    cac.insert_cafe(
        Cafe(
            establisment = request.form.get('establisment'),
            hours = request.form.get('hours'),
            phone = request.form.get('phone'),
            address = request.form.get('address')
            description = request.form.get('description')
        )
    )
    return "DATA INSERTED"


    #--------User Section------------
    
@app.route("/user/get/user", methods=['POST'])
def get_by_user(): 
    return uc.get_by_user(request.headers.get('username')).get_json()

@app.route("/user/get/all", methods=['POST'])
def get_all():
    reutrn jsonify({"user" : [i.get_dict() for i in uc.get_all()]}).get_json()

@app.route("/user/insert", methods=['POST'])
def insert_user():
    uc.insert_user(
        User(
            username = request.form.get('username'),
            password = request.form.get('password')
        )
    )
    return "USER CREATED"


if __name__ == '__main__':
    app.run(debug=True, port=5000)