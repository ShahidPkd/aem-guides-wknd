// Example of how a component should be initialized via JavaScript
// This script logs the value of the component's text property model message to the console

(function () {
    "use strict";

    // Best practice:
    // For a good separation of concerns, don't rely on the DOM structure or CSS selectors,
    // but use dedicated data attributes to identify all elements that the script needs to
    // interact with.
    var selectors = {
        self: '[data-cmp-customheader="customheader"]'
    };

    function CustomHeader(config) {

        function init(config) {
            // Best practice:
            // To prevents multiple initialization, remove the main data attribute that
            // identified the component.
            config.element.removeAttribute("data-cmp-customheader");

            if ($(".cmp-customsearch").length > 0) {

                // var clickCheck = false;

                $("#customSearch").on("click", function () {

                    $(".cmp-mainheader").hide();
                    $(".cmp-customsearch").removeClass("hidden");
                    $(".cmp-customsearch-container").css({"opacity": "1"});
                });


                $(document).click(function (e) {
                    // debugger;
                    var $target = $(e.target);

                    // var checkkk = $target == $clicktarget;

                    if (!$target.is(".cmp-customsearch") && !$target.is(".cmp-customsearch-container") && !$target.is(".cmp-customsearch-container__wrapper") && !$target.is(".cmp-customsearch-container__input") && !$target.is("#searchResultMatch") && !$target.is(".cmp-customsearch-container__search") && !$target.is("#customSearchBtn")) {
                        // debugger;
                        if(!$target.is("#customSearch")){
                            $(".cmp-customsearch").addClass("hidden");
                            $(".cmp-mainheader").show();
                        }
                    }

                });


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
            new CustomHeader({
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