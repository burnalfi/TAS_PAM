import os

from backend.controller.credentials import CredentialController 

from backend.model.credentials import Credentials

from database import Database

from flask import Flask, request, jsonify 

app = Flask(__name__)
app.secret_key = os.urandom(24)

app.config['SECRET_KEY'] = '414c61523ea509b476bdba060a49d3a3'

db = Database(user="root", password="", host="127.0.0.1", database="flasktest")

cc = CredentialController(db)

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


if __name__ == '__main__':
    app.run(debug=True, port=5000)