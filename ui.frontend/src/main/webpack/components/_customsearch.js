// Example of how a component should be initialized via JavaScript
// This script logs the value of the component's text property model message to the console

(function () {
    "use strict";

    // Best practice:
    // For a good separation of concerns, don't rely on the DOM structure or CSS selectors,
    // but use dedicated data attributes to identify all elements that the script needs to
    // interact with.
    var selectors = {
        self: '[data-cmp-customsearch="customsearch"]'
        // property:  '[data-cmp-hook-helloworld="property"]',
        // message:   '[data-cmp-hook-helloworld="model"]'
    };

    function CustomSearch(config) {

        function init(config) {
            // Best practice:
            // To prevents multiple initialization, remove the main data attribute that
            // identified the component.
            config.element.removeAttribute("data-cmp-customsearch");

            function foundResult(msg) {
                debugger;
                var listOfMatch = $("#searchResultMatch");
                console.log("Hello 0");
                for (var i = 0; i < msg.length; i++) {
                    var htmls = `<li><a href="${msg[i].path}"><span>${msg[i].title}</span></a></li>`;
                    listOfMatch.append(htmls);
                }
            }

            if ($(".cmp-customsearch").length > 0) {
                $(".cmp-customsearch-container__input").on("propertychange input", function () {

                    // debugger;
                    var listOfMatch = $("#searchResultMatch");
                    listOfMatch.empty();

                    if ($(".cmp-customsearch-container__input").val().length > 0) {

                        $.ajax({
                            type: "GET",
                            url: "/bin/search?" +
                                $.param({
                                    query: $(".cmp-customsearch-container__input").val(),
                                    page: "1"
                                }),
                            // cache: false,
                            // contentType: false,
                            // processData: false,
                            dataType: "json",
                            success: function (msg) {
                                //form submission worked

                                foundResult(msg);


                                //   $("#confirmationModalClose").on("click", function () {
                                //     confirmationModal.hide();
                                //   });
                            },
                            statusCode: {
                                200: function () {
                                    // submitted = true;
                                    // getEstimateModal.hide();
                                    // var confirmationModal = new Modal(document.getElementById('confirmationModal'), {});
                                    // confirmationModal.show();
                                    console.log(" 200")

                                    // $("#confirmationModalClose").on("click", function () {
                                    //   confirmationModal.hide();
                                    // });
                                }
                            },
                            error: function (req, status, error) {
                                //   alert("Sorry, an error occurred.");
                                //   $(this).removeAttr("disabled");
                                console.log(error + " h error ");
                            }
                        });
                    }



                })
            }

            // var property = config.element.querySelectorAll(selectors.property);
            // property = property.length == 1 ? property[0].textContent : null;

            // var model = config.element.querySelectorAll(selectors.message);
            // model = model.length == 1 ? model[0].textContent : null;

            // $('#mysearch').on("click", function(){
            //     console.log("Clicked S1")
            // })
            // var mysearch = document.getElementById('mysearch');
            // mysearch.addEventListener('click', function(){
            //     console.log("Clicked S")
            // })

            if (console && console.log) {
                // console.log(
                //     "HelloWorld component JavaScript example",
                //     "\nText property:\n", property,
                //     "\nModel message:\n", model
                // );
            }
        }

        if (config && config.element) {
            init(config);
        }
    }

    // Best practice:
    // Use a method like this mutation obeserver to also properly initialize the component
    // when an author drops it onto the page or modified it with the dialog.
    function onDocumentReady() {
        var elements = document.querySelectorAll(selectors.self);

        var count = 0;

        for (var i = 0; i < elements.length; i++) {
            count++;
            new CustomSearch({
                element: elements[i]
            });
        }
        console.log("Test count " + count);

        var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
        var body = document.querySelector("body");
        var observer = new MutationObserver(function (mutations) {
            mutations.forEach(function (mutation) {
                // needed for IE
                var nodesArray = [].slice.call(mutation.addedNodes);
                if (nodesArray.length > 0) {
                    nodesArray.forEach(function (addedNode) {
                        if (addedNode.querySelectorAll) {
                            var elementsArray = [].slice.call(addedNode.querySelectorAll(selectors.self));
                            elementsArray.forEach(function (element) {
                                new HelloWorld({
                                    element: element
                                });
                            });
                        }
                    });
                }
            });
        });

        observer.observe(body, {
            subtree: true,
            childList: true,
            characterData: true
        });
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }

}());